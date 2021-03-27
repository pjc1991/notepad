package com.pjc.notepad.member.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    private String memberId;

    @Column(length = 20, nullable = false)
    private String memberPw;

    @Column(length = 40)
    private String memberMail;
    private Integer memberStatus;
    private Integer memberAdmin;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date memberRegDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date memberLastLoginDate;

    public void setMemberLastLoginDate() {
        this.memberLastLoginDate = new Date();
    }

    public void setMemberRegDate() {
        this.memberRegDate = new Date();
    }

}
