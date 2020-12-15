package com.pjc.notepad.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pjc.notepad.service.HomeService;
import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Resource(name = "HomeService")
    HomeService hs;

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        logger.info("loginForm RemoteAddr : " + request.getRemoteAddr());

        return "redirect:/loginForm.do";
    }

    @RequestMapping("loginForm.do")
    public String loginForm(Model model, HttpServletRequest request) {
        logger.info("loginForm RemoteAddr : " + request.getRemoteAddr());

        return "loginForm";
    }

    @RequestMapping("loginPro.do")
    public String loginPro(Model model, HttpServletRequest request, MemberDto dto,
            RedirectAttributes redirectAttributes) {

        logger.info("loginPro RemoteAddr : " + request.getRemoteAddr());
        logger.info("MemberDto m_id : " + dto.getM_id());
        logger.info("MemberDto m_pw : " + dto.getM_pw());

        dto = hs.loginPro(dto);

        String msg;
        String returnTarget;

        if (dto != null) {
            logger.info("result => " + dto.getM_status());
            request.getSession().setAttribute("MemberDto", dto);
            msg = "성공적으로 로그인되었습니다. " + dto.getM_id() + "님.";
            returnTarget = "redirect:noteList.do";
        } else {
            msg = "로그인에 실패하셨습니다.";
            returnTarget = "redirect:loginForm.do";
        }
        logger.info(msg);
        redirectAttributes.addFlashAttribute("msg", msg);

        return returnTarget;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/loginForm.do";
    }

    @RequestMapping("noteList.do")
    public String noteList(Model model, HttpServletRequest request, NoteDto dto) {
        HttpSession session = request.getSession();
        if (session.getAttribute("MemberDto") == null) {
            return "redirect:/loginForm.do";
        }

        logger.info("noteList RemoteAddr : " + request.getRemoteAddr());
        MemberDto memberDto = (MemberDto) request.getSession().getAttribute("MemberDto");
        List<NoteDto> list = hs.noteList(memberDto);
        if (list.size() > 0) {
            logger.info(list.get(0).getN_regdate().toString());
        }
        model.addAttribute("noteList", list);

        return "noteList";
    }

    @RequestMapping("insertNote.do")
    public String insertNote(Model model, HttpServletRequest request, NoteDto dto,
            RedirectAttributes redirectAttributes) {
        logger.info("insertNote RemoteAddr : " + request.getRemoteAddr());
        HttpSession session = request.getSession();
        MemberDto loginDto = (MemberDto) session.getAttribute("MemberDto");

        dto.setM_id(loginDto.getM_id());
        dto.setN_status(1);
        int result = hs.insertNote(dto);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("msg", "입력 성공");
        } else {
            redirectAttributes.addFlashAttribute("msg", "입력 실패");
        }

        return "redirect:/noteList.do";
    }

}
