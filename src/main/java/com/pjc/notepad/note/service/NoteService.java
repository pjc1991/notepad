package com.pjc.notepad.note.service;

import java.util.List;

import com.pjc.notepad.note.service.dto.NoteDto;

public interface NoteService {

    List<NoteDto> GetByMemberId(String memberId);

    NoteDto insertNote(NoteDto note);

    NoteDto getByNoteIdx(Integer noteIdx);

    void DeleteByNoteIdx(int noteIdx);

}
