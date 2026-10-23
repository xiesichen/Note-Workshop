package com.noteworkshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记图片实体类，对应数据库表 note_image
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteImage {

    /** 图片id */
    private Integer id;

    /** 所属笔记id，关联 note.id */
    private Integer noteId;

    /** 图片地址 */
    private String url;

    /** 排序，越小越靠前 */
    private Integer sort;
}