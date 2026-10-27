package com.noteworkshop.service;

import com.noteworkshop.pojo.Note;

import java.util.List;
import java.util.Map;

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


    /**
     * 分页查询"我的笔记"（含私有，不含已删除），每页固定 5 条
     *
     * @return {"list": [...], "total": 总数, "page": 当前页, "size": 每页条数, "pages": 总页数}
     */
    Map<String, Object> findMyNotes(Integer userId, int page);


    /**
     * 热门榜：公开笔记按点赞数排名（分页），每页固定 5 条
     */
    Map<String, Object> findHotNotes(int page);
}
