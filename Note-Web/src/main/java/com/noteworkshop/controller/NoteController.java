package com.noteworkshop.controller;

import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    /** 从拦截器放进 request 的属性里取当前登录用户 id */
    private Integer currentUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    // ==================== 公开接口（不需要登录）====================

    @GetMapping("/list")
    public List<Note> list() {
        return noteService.findAll();
    }

    @GetMapping("/detail/{id}")
    public Note detail(@PathVariable Integer id) {
        log.info("查询笔记id: {}", id);
        return noteService.findById(id);
    }

    @GetMapping("/search")
    public List<Note> search(@RequestParam String keyword) {
        return noteService.search(keyword);
    }

    /**
     * 热门榜：按点赞数排名（公开接口）
     * GET /note/hot?page=1&size=10
     */
    @GetMapping("/hot")
    public Map<String, Object> hot(@RequestParam(defaultValue = "1") int page) {
        return noteService.findHotNotes(page);
    }

    // ==================== 需要登录的接口 ====================

    /**
     * 新增笔记
     *
     * ★ userId 从 token 里取，不用前端传的——防止冒充别人发笔记
     */
    @PostMapping("/add")
    public Note add(@RequestBody Note note, HttpServletRequest request) {
        Integer userId = currentUserId(request);
        note.setUserId(userId);

        log.info("添加笔记: userId={}, title={}", userId, note.getTitle());
        noteService.add(note);
        return note;
    }

    /**
     * 修改笔记
     *
     * ★ 只有作者本人能改
     */
    @PutMapping("/update")
    public Note update(@RequestBody Note note, HttpServletRequest request) {
        Integer userId = currentUserId(request);

        Note old = noteService.findById(note.getId());
        if (old == null) {
            throw new RuntimeException("笔记不存在或已被删除");
        }
        if (!old.getUserId().equals(userId)) {
            throw new RuntimeException("只能修改自己写的笔记");
        }

        log.info("更新笔记: id={}, userId={}", note.getId(), userId);
        noteService.update(note);
        return noteService.findById(note.getId());
    }

    /**
     * 分页查询"我的笔记"（含私有，不含已删除）
     *
     * GET /note/my?page=1&size=10
     *
     * 需要登录（拦截器里配了）
     */
    @GetMapping("/my")
    public Map<String, Object> myNotes(@RequestParam(defaultValue = "1") int page,
                                       HttpServletRequest request) {
        Integer userId = currentUserId(request);
        log.info("查询我的笔记，userId={}, page={}", userId, page);
        return noteService.findMyNotes(userId, page);
    }

    /**
     * 删除笔记
     *
     * ★ 只有作者本人能删
     */
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer noteId, HttpServletRequest request) {
        Integer userId = currentUserId(request);

        Note old = noteService.findById(noteId);
        if (old == null) {
            throw new RuntimeException("笔记不存在或已被删除");
        }
        if (!old.getUserId().equals(userId)) {
            throw new RuntimeException("只能删除自己写的笔记");
        }

        log.info("删除笔记: id={}, userId={}", noteId, userId);
        noteService.deleteById(noteId);
    }
}
