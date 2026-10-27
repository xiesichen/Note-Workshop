package com.noteworkshop.controller;

import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public List<Note> list() {
        return noteService.findAll();
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer noteId) {
        noteService.deleteById(noteId);
        log.info("删除笔记id: {}", noteId);
    }

    @PostMapping("/add")
    public Note add(@RequestBody Note note) {
        noteService.add(note);
        log.info("添加笔记: {}", note);
        return note;      // 返回带 id 的对象
    }
    @GetMapping("/detail/{id}")
    public Note detail(@PathVariable Integer id) {
        log.info("查询笔记id: {}", id);
        return noteService.findById(id);

    }
    @PutMapping("/update")
    public Note update(@RequestBody Note note) {
        noteService.update(note);
        log.info("更新笔记: {}", note);
        return noteService.findById(note.getId());    // 返回更新后的最新数据
    }


    /**
     * 按关键字搜索笔记
     * 访问：GET /note/search?keyword=大肥鱼
     */
    @GetMapping("/search")
    public List<Note> search(@RequestParam String keyword) {
        return noteService.search(keyword);
    }
}