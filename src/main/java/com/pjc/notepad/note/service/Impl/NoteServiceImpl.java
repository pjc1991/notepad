package com.pjc.notepad.note.service.Impl;

import java.util.List;

import com.pjc.notepad.note.service.Note;
import com.pjc.notepad.note.service.NoteRepository;
import com.pjc.notepad.note.service.NoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public List<Note> GetByMemberId(String memberId) {
        return noteRepository.findByMemberId(memberId);
    }

    @Override
    public Note insertNote(Note note) {
        return noteRepository.save(note);
    }

}
