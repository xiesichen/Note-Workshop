package com.noteworkshop.service;

import com.noteworkshop.pojo.Note;

import java.util.List;

public interface NoteService {

    /**
     * 查询所有未删除的笔记
     */
    List<Note> findAll();
}
