package com.pjc.notepad.member.service;

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
        Member member = (Member) target;
        if (memberRepository.findById(member.getMemberId()).isPresent()) {
            errors.rejectValue("memberId", "중복되는 아이디가 존재합니다.");
        }
        ;

    }

}
