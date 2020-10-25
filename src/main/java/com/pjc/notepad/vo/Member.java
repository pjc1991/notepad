package com.pjc.notepad.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Member {

    private String m_id;
    private String m_pw;
    private String m_mail;
    private Integer m_status;
    private Integer m_admin;
    private Date m_regdate;

}
