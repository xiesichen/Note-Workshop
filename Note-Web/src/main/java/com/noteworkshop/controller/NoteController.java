package com.noteworkshop.controller;

import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    }

    @PostMapping("/add")
    public Note add(@RequestBody Note note) {
        noteService.add(note);
        return note;      // 返回带 id 的对象
    }
    @GetMapping("/detail/{id}")
    public Note detail(@PathVariable Integer id) {
        return noteService.findById(id);
    }
    @PutMapping("/update")
    public Note update(@RequestBody Note note) {
        noteService.update(note);
        return noteService.findById(note.getId());    // 返回更新后的最新数据
    }
}