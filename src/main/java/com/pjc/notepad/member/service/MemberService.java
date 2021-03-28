package com.pjc.notepad.member.service;

import com.pjc.notepad.member.service.dto.MemberDto;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    MemberDto login(MemberDto memberDto);

    int insertMember(MemberDto memberDto);

}
