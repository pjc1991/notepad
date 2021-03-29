package com.pjc.notepad.note.service;

import java.util.List;

import com.pjc.notepad.note.service.entity.Note;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByMemberId(String memberId);

    List<Note> findByMemberIdOrderByNoteRegdateDesc(String memberId);

}
