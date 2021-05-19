package com.pjc.notepad.member.service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDto {

    private String memberId;
    private String memberPw;
    private String memberMail;
    private Integer memberStatus;
    private Integer memberAdmin;
    private Date memberRegDate;
    private Date memberLastLoginDate;

}
