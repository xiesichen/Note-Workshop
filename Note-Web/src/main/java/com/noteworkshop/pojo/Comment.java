package com.noteworkshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论实体类，对应数据库表 comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    /** 评论id */
    private Integer id;

    /** 所属笔记id，关联 note.id */
    private Integer noteId;

    /** 评论人id，关联 user.id */
    private Integer userId;

    /** 父评论id，0表示顶级评论 */
    private Integer parentId;

    /** 评论内容 */
    private String content;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 逻辑删除 0未删 1已删 */
    private Integer deleted;
}