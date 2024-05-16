package com.coursework.edem.EdemBackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()

public class EdemController {
    @GetMapping("/")
    public String homePage(Model model) {
        return "home/index";
    }


}
