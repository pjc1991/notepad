package com.pjc.notepad.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class Note {

    private Integer n_idx;
    private String m_id;
    private String n_title;
    private String n_content;
    private Integer n_status;
    private Date n_regdate;

}
