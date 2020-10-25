package com.pjc.notepad.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.pjc.notepad.dao.HomeDao;
import com.pjc.notepad.service.HomeService;
import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

import org.springframework.stereotype.Service;

@Service("HomeService")
public class HomeServiceImpl implements HomeService {

    @Resource(name = "HomeDao")
    HomeDao hd;

    @Override
    public MemberDto loginPro(MemberDto dto) {
        return hd.loginPro(dto);
    }

    @Override
    public List<NoteDto> noteList(MemberDto memberDto) {
        return hd.noteList(memberDto);
    }

    @Override
    public int insertNote(NoteDto dto) {
        return hd.insertNote(dto);
    }

}
