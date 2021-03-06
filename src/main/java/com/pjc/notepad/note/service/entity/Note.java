package com.pjc.notepad.note.service.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date noteRegdate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date noteLastUpdate;

    // @PrePersist
    // void preInsert() {
    // if (this.noteRegdate == null) {
    // this.noteRegdate = new Date();
    // }
    // if (this.noteLastUpdate == null) {
    // this.noteLastUpdate = new Date();
    // }
    // }

}
