package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.CommentMapper;
import com.noteworkshop.pojo.Comment;
import com.noteworkshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listByNoteId(Integer noteId) {
        // 查询没有业务逻辑，直接转发
        return commentMapper.findByNoteId(noteId);
    }

    @Override
    public void add(Comment comment) {
        // ① 内容不能为空
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new RuntimeException("评论内容不能为空");
        }

        // ② 长度限制（数据库 varchar(500)）
        if (comment.getContent().length() > 500) {
            throw new RuntimeException("评论不能超过 500 字");
        }

        // ③ 必须有评论人
        if (comment.getUserId() == null) {
            throw new RuntimeException("缺少评论人");
        }

        // ④ 没传 parentId 就当顶级评论
        if (comment.getParentId() == null) {
            comment.setParentId(0);
        }

        commentMapper.insert(comment);
    }
}