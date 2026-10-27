package com.noteworkshop.service;

import com.noteworkshop.pojo.Note;

import java.util.List;

public interface NoteService {

    /**
     * 查询所有未删除的笔记
     */
    List<Note> findAll();

    void deleteById(Integer noteId);

    void add(Note note);

    Note findById(Integer id);

    void update(Note note);


    /**
     * 按关键字搜索笔记
     */
    List<Note> search(String kw);
}
