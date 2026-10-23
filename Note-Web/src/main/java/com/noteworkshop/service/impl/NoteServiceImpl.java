package com.noteworkshop.service.impl;

import com.noteworkshop.mapper.NoteMapper;
import com.noteworkshop.pojo.Note;
import com.noteworkshop.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public List<Note> findAll() {
        return noteMapper.findAll();
    }
}
