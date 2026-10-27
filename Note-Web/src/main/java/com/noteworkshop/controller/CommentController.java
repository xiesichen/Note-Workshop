package com.noteworkshop.controller;

import com.noteworkshop.pojo.Comment;
import com.noteworkshop.service.CommentService;
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
     * 查询某篇笔记的所有评论
     * 访问：GET /comment/list?noteId=1
     */
    @GetMapping("/list")
    public List<Comment> list(@RequestParam Integer noteId) {
        log.info("查询评论，noteId={}", noteId);
        return commentService.listByNoteId(noteId);
    }

    /**
     * 新增评论
     * 请求体：{"noteId":1, "userId":2, "parentId":0, "content":"写得不错"}
     */
    @PostMapping("/add")
    public Comment add(@RequestBody Comment comment) {
        log.info("新增评论，noteId={}, userId={}, content={}",
                comment.getNoteId(), comment.getUserId(), comment.getContent());
        commentService.add(comment);
        return comment;
    }
}