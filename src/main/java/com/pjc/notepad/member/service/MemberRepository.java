package com.pjc.notepad.member.service;

import com.pjc.notepad.member.service.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {

}
