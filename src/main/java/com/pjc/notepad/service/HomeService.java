package com.pjc.notepad.service;

import java.util.List;

import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

public interface HomeService {

	MemberDto loginPro(MemberDto dto);

	List<NoteDto> noteList(MemberDto memberDto);

	int insertNote(NoteDto dto);
    
}
