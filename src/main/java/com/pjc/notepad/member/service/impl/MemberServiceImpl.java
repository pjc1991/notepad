package com.pjc.notepad.member.service.impl;

import java.util.Optional;

import com.pjc.notepad.member.service.entity.Member;
import com.pjc.notepad.util.ModelMapperUtil;
import com.pjc.notepad.member.service.MemberRepository;
import com.pjc.notepad.member.service.MemberService;
import com.pjc.notepad.member.service.dto.MemberDto;

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
    public MemberDto login(MemberDto memberDto) {
        LOGGER.debug("login  member.getMemberId() = {}", memberDto.getMemberId());
        Optional<Member> memberOptional = memberRepository.findById(memberDto.getMemberId());
        // ID doesn't exists
        if (!memberOptional.isPresent()) {
            // if it's not, it's null
            return null;
        } else if (!memberOptional.get().getMemberPw().equals(memberDto.getMemberPw())) {
            // wrong password, null
            return null;
        } else {
            return ModelMapperUtil.getModelMapper().map(memberOptional.get(), MemberDto.class);
        }

    }

    @Override
    public int insertMember(MemberDto memberDto) {
        LOGGER.info("login  member.getMemberId() = {}", memberDto.getMemberId());
        Member member = ModelMapperUtil.getModelMapper().map(memberDto, Member.class);
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
