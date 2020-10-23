package com.pjc.notepad.vo;

import lombok.Data;

@Data
public class Note {
    
    private int n_idx;
    private String m_id; 
    private String n_title;
    private String n_content;
    private int n_status;
    
}
