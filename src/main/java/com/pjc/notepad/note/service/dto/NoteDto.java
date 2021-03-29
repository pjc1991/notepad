package com.pjc.notepad.note.service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto {

    private Integer noteIdx;
    private String memberId;
    private String noteTitle;
    private String noteContent;
    private Integer noteStatus;

    private Date noteRegdate;
    private Date noteLastUpdate;

}
