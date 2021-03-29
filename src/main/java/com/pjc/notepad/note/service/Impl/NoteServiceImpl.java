package com.pjc.notepad.note.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pjc.notepad.note.service.NoteRepository;
import com.pjc.notepad.note.service.NoteService;
import com.pjc.notepad.note.service.dto.NoteDto;
import com.pjc.notepad.note.service.entity.Note;
import com.pjc.notepad.util.ModelMapperUtil;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;

    @Override
    public List<NoteDto> GetByMemberId(String memberId) {
        // return noteRepository.findByMemberId(memberId);
        // better be ordered by regdate desc
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
        Optional<Note> noteOptional = noteRepository.findById(noteIdx);
        if (!noteOptional.isPresent()) {
            return null;
        } else {
            Note note = noteOptional.get();
            return ModelMapperUtil.getModelMapper().map(note, NoteDto.class);
        }
    }

    @Override
    public void DeleteByNoteIdx(int noteIdx) {
        noteRepository.deleteById(noteIdx);
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) {
        Optional<Note> noteOptional = noteRepository.findById(noteDto.getNoteIdx());
        if (!noteOptional.isPresent()) {
            return null;
        } else {
            Note note = noteOptional.get();
            note.setNoteContent(noteDto.getNoteContent());
            note.setNoteTitle(noteDto.getNoteTitle());
            note.setNoteLastUpdate(new Date());
            noteRepository.save(note);
            return ModelMapperUtil.getModelMapper().map(note, NoteDto.class);
        }
    }

}
