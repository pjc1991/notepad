package com.pjc.notepad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/showHome")
    public String showHome(Model model) {
        return "home";
    }
    
}
