package com.pjc.notepad.member.service;

import com.pjc.notepad.member.service.dto.MemberDto;
import com.pjc.notepad.member.service.entity.Member;
import com.pjc.notepad.util.ModelMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberJoinValidator implements Validator {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {

        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberDto memberDto = (MemberDto) target;
        if(memberDto.getMemberId() == null){
            errors.rejectValue("memberId", "memberIdNull");
        }
        Member member = ModelMapperUtil.getModelMapper().map((MemberDto) target, Member.class);
        if (memberRepository.findById(member.getMemberId()).isPresent()) {
            errors.rejectValue("memberId", "중복되는 아이디가 존재합니다.");
        }

    }

}
