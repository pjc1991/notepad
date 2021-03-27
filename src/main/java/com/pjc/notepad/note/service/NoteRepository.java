package com.pjc.notepad.note.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByMemberId(String memberId);

}
