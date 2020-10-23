package com.pjc.notepad.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Member {

    private String m_id;
    private String m_pw;
    private String m_mail;
    private int m_status;
    private int m_admin;
    private Date m_regdate;


    
}
