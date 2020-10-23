package com.pjc.notepad.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.pjc.notepad.service.HomeService;
import com.pjc.notepad.vo.impl.MemberDto;
import com.pjc.notepad.vo.impl.NoteDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Resource(name = "HomeService")
    HomeService hs;

    @RequestMapping("loginForm.do")
    public String loginForm(Model model, HttpServletRequest request) {
        logger.info("loginForm RemoteAddr : " + request.getRemoteAddr());

        return "loginForm";
    }

    @RequestMapping("loginPro.do")
    public String loginPro(Model model, HttpServletRequest request, MemberDto dto){

        logger.info("loginPro RemoteAddr : " + request.getRemoteAddr());
        logger.info("MemberDto m_id : " + dto.getM_id());
        logger.info("MemberDto m_pw : " + dto.getM_pw());

        dto = hs.loginPro(dto);
        if(dto != null) {
            logger.info("result => " + dto.getM_status());
            request.getSession().setAttribute("MemberDto", dto);
        }


        return "redirect:loginForm.do";
    }

    @RequestMapping("noteList.do")
    public String noteList(Model model, HttpServletRequest request, NoteDto dto){

        logger.info("noteList RemoteAddr : " + request.getRemoteAddr());
        MemberDto memberDto = (MemberDto) request.getSession().getAttribute("MemberDto");

        List<NoteDto> list = hs.noteList(memberDto);

        model.addAttribute("noteList", list);

        return "noteList";
    }

    @RequestMapping("/home")
    public String Home (Model model) {
        return "home";
    }

    @RequestMapping("/test")
    public String test(Model model){

        logger.info("Test init. ");
        model.addAttribute("msg", "Hello, This is from Controller. ");
        
        return "default";
    }
    
}
