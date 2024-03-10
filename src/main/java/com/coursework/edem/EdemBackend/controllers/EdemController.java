package com.coursework.edem.EdemBackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class EdemController {

    @GetMapping("/")
    public String homePage() {
        return "home/main";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        //model.addAttribute("title", "Про нас:");
        return "home/registration";
    }



}
