package com.pjc.notepad.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/loginForm")
    public String loginForm(Model model) {
        return "loginForm";
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
