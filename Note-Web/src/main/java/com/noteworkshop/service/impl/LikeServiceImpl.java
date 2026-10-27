package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.LikeMapper;
import com.noteworkshop.mapper.NoteMapper;
import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public boolean toggle(Integer noteId, Integer userId) {
        if (noteId == null || userId == null) {
            throw new RuntimeException("参数不能为空");
        }

        // ① 笔记必须存在
        Note note = noteMapper.findById(noteId);
        if (note == null) {
            throw new RuntimeException("笔记不存在或已被删除");
        }

        // ② 只能给公开笔记点赞
        if (!Integer.valueOf(1).equals(note.getIsPublic())) {
            throw new RuntimeException("只能给公开笔记点赞");
        }

        // ③ 点过了 → 取消；没点过 → 点赞
        int cnt = likeMapper.exists(noteId, userId);

        if (cnt > 0) {
            likeMapper.delete(noteId, userId);
            log.info("取消点赞: noteId={}, userId={}", noteId, userId);
            return false;
        } else {
            likeMapper.insert(noteId, userId);
            log.info("点赞: noteId={}, userId={}", noteId, userId);
            return true;
        }
    }

    /** 每页固定 5 条 */
    private static final int PAGE_SIZE = 5;

    @Override
    public Map<String, Object> myLikedNotes(Integer userId, int page) {
        // 页码最小是 1（否则 offset 会变成负数，SQL 报错）
        int p = Math.max(page, 1);

        // 第 1 页 offset=0，第 2 页 offset=5，第 3 页 offset=10 ...
        int offset = (p - 1) * PAGE_SIZE;

        // 查这一页的数据 + 查总条数（分页要两个信息）
        List<Note> list = noteMapper.findLikedByUserId(userId, offset, PAGE_SIZE);
        int total = noteMapper.countLikedByUserId(userId);

        // 总页数（向上取整）
        int pages = (total + PAGE_SIZE - 1) / PAGE_SIZE;

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", p);
        result.put("size", PAGE_SIZE);
        result.put("pages", pages);
        return result;
    }
}
