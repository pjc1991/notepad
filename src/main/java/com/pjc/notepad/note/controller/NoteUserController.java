package com.pjc.notepad.note.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pjc.notepad.member.service.dto.MemberDto;
import com.pjc.notepad.note.service.NoteService;
import com.pjc.notepad.note.service.dto.NoteDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NoteUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteUserController.class);

    @Autowired
    NoteService noteService;

    @RequestMapping(value = "/note", method = RequestMethod.GET)
    public String noteList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/login";
        }
        LOGGER.info("noteList RemoteAddr : " + request.getRemoteAddr());
        MemberDto loginUser = (MemberDto) request.getSession().getAttribute("currentUser");

        List<NoteDto> list = noteService.GetByMemberId(loginUser.getMemberId());
        if (list.size() > 0) {
            LOGGER.info(list.get(0).getNoteRegdate().toString());
        }
        model.addAttribute("noteList", list);

        return "note/list";
    }

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public String insertNote(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
            NoteDto note) {
        LOGGER.info("insertNote RemoteAddr : " + request.getRemoteAddr());
        HttpSession session = request.getSession();
        MemberDto loginUser = (MemberDto) session.getAttribute("currentUser");

        note.setMemberId(loginUser.getMemberId());
        note.setNoteStatus(1);
        note.setNoteLastUpdate(new Date());
        note.setNoteRegdate(new Date());
        note = noteService.insertNote(note);
        if (note.getNoteIdx() != null) {
            redirectAttributes.addFlashAttribute("msg", "입력 성공");
        } else {
            redirectAttributes.addFlashAttribute("msg", "입력 실패");
        }
        return "redirect:/note";
    }

    @RequestMapping(value = "/note", method = RequestMethod.PUT)
    @ResponseBody
    public NoteDto NotePutApi(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
            @RequestBody NoteDto noteDto, HttpSession session) {
        MemberDto currentUser = (MemberDto) session.getAttribute("currentUser");
        LOGGER.info("NoteDto.getNoteIdx : {}", noteDto.getNoteIdx());
        if (currentUser == null) {
            // something happens when you are not logged in
            // should return error, but how?
            return null;
        }

        // 노트 소유자 확인
        NoteDto fromDB = noteService.getByNoteIdx(noteDto.getNoteIdx());
        if (!currentUser.getMemberId().equals(fromDB.getMemberId())) {
            // you are trying to modify notes of someone else
            return null;
        } else {
            // everything is fine
            noteDto = noteService.updateNote(noteDto);
        }
        return noteDto;
    }

    @RequestMapping(value = "/note/{noteIdx}", method = RequestMethod.GET)
    public String NoteFormGet(Model model, @ModelAttribute("note") NoteDto noteDto, BindingResult bindingResult,
            HttpSession session, @PathVariable("noteIdx") Integer noteIdx) {
        MemberDto currentUser = (MemberDto) session.getAttribute("currentUser");
        if (currentUser == null) {
            // you are not logged in
            return "redirect:/login";
        }
        NoteDto fromDB = noteService.getByNoteIdx(noteIdx);
        if (!currentUser.getMemberId().equals(fromDB.getMemberId())) {
            // it's not your notes
            return "redirect:/note";
        }
        model.addAttribute("note", fromDB);
        return "note/form";
    }

    @RequestMapping(value = "/note/delete/{noteIdx}", method = RequestMethod.GET)
    public String NoteDeleteApi(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
            @PathVariable("noteIdx") String noteIdx) {
        HttpSession session = request.getSession();
        MemberDto currentUser = (MemberDto) session.getAttribute("currentUser");
        LOGGER.info("currentUser is {}", currentUser.toString());
        try {
            int noteIdxParsed = Integer.parseInt(noteIdx);
            NoteDto noteDto = noteService.getByNoteIdx(noteIdxParsed);
            LOGGER.info("noteDto is {}", noteDto.toString());
            if (noteDto.getMemberId().equals(currentUser.getMemberId())) {
                noteService.DeleteByNoteIdx(noteIdxParsed);
            }
        } catch (NumberFormatException e) {
            LOGGER.error("noteIdx({}) isn't number", noteIdx);
        }
        return "redirect:/note";
    }
}
