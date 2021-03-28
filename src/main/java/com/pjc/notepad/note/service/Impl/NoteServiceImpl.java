package com.pjc.notepad.note.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pjc.notepad.note.service.NoteRepository;
import com.pjc.notepad.note.service.NoteService;
import com.pjc.notepad.note.service.dto.NoteDto;
import com.pjc.notepad.note.service.entity.Note;
import com.pjc.notepad.util.ModelMapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Override
    public List<NoteDto> GetByMemberId(String memberId) {
        // return noteRepository.findByMemberId(memberId);
        List<Note> noteList = noteRepository.findByMemberIdOrderByNoteRegdateDesc(memberId);
        List<NoteDto> noteDtoList = noteList.stream()
                .map(note -> ModelMapperUtil.getModelMapper().map(note, NoteDto.class)).collect(Collectors.toList());
        return noteDtoList;
    }

    @Override
    public NoteDto insertNote(NoteDto noteDto) {
        return ModelMapperUtil.getModelMapper()
                .map(noteRepository.save(ModelMapperUtil.getModelMapper().map(noteDto, Note.class)), NoteDto.class);
    }

    @Override
    public NoteDto getByNoteIdx(Integer noteIdx) {
        Optional<Note> note = noteRepository.findById(noteIdx);
        if (!note.isPresent()) {
            return null;
        } else {
            return ModelMapperUtil.getModelMapper().map(note, NoteDto.class);
        }
    }

    @Override
    public void DeleteByNoteIdx(int noteIdx) {
        noteRepository.deleteById(noteIdx);
    }

}
