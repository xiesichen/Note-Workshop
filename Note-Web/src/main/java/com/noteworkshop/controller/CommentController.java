package com.noteworkshop.controller;

import com.noteworkshop.pojo.Comment;
import com.noteworkshop.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 查询某篇笔记的所有评论（公开接口）
     * GET /comment/list?noteId=1
     */
    @GetMapping("/list")
    public List<Comment> list(@RequestParam Integer noteId) {
        log.info("查询评论，noteId={}", noteId);
        return commentService.listByNoteId(noteId);
    }

    /**
     * 新增评论（需要登录）
     *
     * ★ userId 从 token 里取，不用前端传的
     */
    @PostMapping("/add")
    public Comment add(@RequestBody Comment comment, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("userId");
        comment.setUserId(userId);

        log.info("新增评论，noteId={}, userId={}", comment.getNoteId(), userId);
        commentService.add(comment);
        return comment;
    }
}
