package com.pjc.notepad.dao;

import java.util.List;

import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

public interface HomeDao {

	MemberDto loginPro(MemberDto dto);

	List<NoteDto> noteList(MemberDto memberDto);
    
}
