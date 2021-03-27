package com.pjc.notepad.member.service.impl;

import java.util.Optional;

import com.pjc.notepad.member.service.Member;
import com.pjc.notepad.member.service.MemberRepository;
import com.pjc.notepad.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member login(Member member) {
        LOGGER.debug("login  member.getMemberId() = {}", member.getMemberId());
        Optional<Member> memberOptional = memberRepository.findById(member.getMemberId());
        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public int insertMember(Member member) {
        LOGGER.info("login  member.getMemberId() = {}", member.getMemberId());
        member.setMemberLastLoginDate();
        member.setMemberRegDate();
        try {
            LOGGER.info("memberId : {}", member.getMemberId());
            memberRepository.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

}
