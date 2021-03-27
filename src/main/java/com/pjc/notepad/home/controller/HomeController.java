package com.pjc.notepad.home.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {
        logger.info("loginForm RemoteAddr : " + request.getRemoteAddr());

        return "redirect:/login";
    }

}
