package com.pjc.notepad.note.service;

import java.util.List;

public interface NoteService {

    List<Note> GetByMemberId(String memberId);

    Note insertNote(Note note);

}
