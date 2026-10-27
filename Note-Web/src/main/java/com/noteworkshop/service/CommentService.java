package com.noteworkshop.service;

import com.noteworkshop.pojo.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 查询某篇笔记的所有评论
     */
    List<Comment> listByNoteId(Integer noteId);

    /**
     * 新增评论
     */
    void add(Comment comment);
}