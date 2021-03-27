package com.pjc.notepad.note.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pjc.notepad.member.service.Member;
import com.pjc.notepad.note.service.Note;
import com.pjc.notepad.note.service.NoteRepository;
import com.pjc.notepad.note.service.NoteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        Member loginUser = (Member) request.getSession().getAttribute("currentUser");

        List<Note> list = noteService.GetByMemberId(loginUser.getMemberId());
        if (list.size() > 0) {
            LOGGER.info(list.get(0).getNoteRegdate().toString());
        }
        model.addAttribute("noteList", list);

        return "noteList";
    }

    @RequestMapping(value = "/note", method = RequestMethod.POST)
    public String insertNote(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
            Note note) {
        LOGGER.info("insertNote RemoteAddr : " + request.getRemoteAddr());
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("currentUser");

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
}
