package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.NoteMapper;
import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public List<Note> findAll() {
        return noteMapper.findAll();
    }

    @Override
    public void deleteById(Integer noteId) {
        noteMapper.deleteById(noteId);

    }

    @Override
    public void add(Note note) {
        // ① 标题不能为空
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new RuntimeException("标题不能为空");
        }

        // ② 标题长度限制（数据库 varchar(100)）
        if (note.getTitle().length() > 100) {
            throw new RuntimeException("标题不能超过 100 个字");
        }

        // ③ 必须有作者
        if (note.getUserId() == null) {
            throw new RuntimeException("缺少作者信息");
        }

        // 要求：笔记必须指定一本图书名字
        if (note.getBookName() == null || note.getBookName().trim().isEmpty()) {
            throw new RuntimeException("请填写图书名称");
        }

        // ④ 默认公开
        if (note.getIsPublic() == null) {
            note.setIsPublic(1);
        }

        // ⑤ 插入
        noteMapper.insert(note);

    }

    @Override
    public Note findById(Integer id) {
        return noteMapper.findById(id);
    }
    @Override
    public void update(Note note) {
        // ① 必须有 id（不然不知道改哪条）
        if (note.getId() == null) {
            throw new RuntimeException("缺少笔记 id");
        }

        // ② 标题不能为空
        if (note.getTitle() == null || note.getTitle().trim().isEmpty()) {
            throw new RuntimeException("标题不能为空");
        }

        // ③ 标题长度限制
        if (note.getTitle().length() > 100) {
            throw new RuntimeException("标题不能超过 100 个字");
        }

        // ④ 图书名不能为空
        if (note.getBookName() == null || note.getBookName().trim().isEmpty()) {
            throw new RuntimeException("请填写图书名称");
        }

        // ⑤ 执行更新，检查是否真的改到了
        int rows = noteMapper.update(note);
        if (rows == 0) {
            throw new RuntimeException("笔记不存在或已被删除");
        }

    }


    @Override
    public List<Note> search(String kw) {
        return noteMapper.search(kw);
    }


    /** 每页固定 5 条 */
    private static final int PAGE_SIZE = 5;

    @Override
    public Map<String, Object> findMyNotes(Integer userId, int page) {
        int p = Math.max(page, 1);              // 页码最小是 1
        int offset = (p - 1) * PAGE_SIZE;       // 第 1 页 0、第 2 页 5、第 3 页 10...

        List<Note> list = noteMapper.findByUserId(userId, offset, PAGE_SIZE);
        int total = noteMapper.countByUserId(userId);
        int pages = (total + PAGE_SIZE - 1) / PAGE_SIZE;

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("total", total);
        result.put("page", p);
        result.put("size", PAGE_SIZE);
        result.put("pages", pages);
        return result;
    }


    @Override
    public Map<String, Object> findHotNotes(int page) {
        int p = Math.max(page, 1);
        int offset = (p - 1) * PAGE_SIZE;

        List<Note> list = noteMapper.findHot(offset, PAGE_SIZE);
        int total = noteMapper.countPublic();
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




