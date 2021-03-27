package com.pjc.notepad.note.service;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteIdx;
    private String memberId;
    private String noteTitle;
    private String noteContent;
    private Integer noteStatus;
    @ColumnDefault("now()")
    private Date noteRegdate;
    @ColumnDefault("now()")
    private Date noteLastUpdate;

}
