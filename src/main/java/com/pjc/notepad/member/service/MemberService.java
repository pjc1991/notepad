package com.pjc.notepad.member.service;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    Member login(Member member);

    int insertMember(Member member);

}
