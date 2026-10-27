package com.noteworkshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 笔记实体类，对应数据库表 note
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    /** 笔记id */
    private Integer id;

    /** 作者id，关联 user.id */
    private Integer userId;

    /** 图书名称（笔记必须指定一本书） */
    private String bookName;

    /** 标题 */
    private String title;

    /** 正文内容 */
    private String content;

    /** 0私有 1公开 */
    private Integer isPublic;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除 0未删 1已删 */
    private Integer deleted;

    /** 点赞数（不是 note 表的列，是统计出来的） */
    private Integer likeCount;
}