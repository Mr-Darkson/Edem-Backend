package com.coursework.edem.EdemBackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class EdemController {

    @GetMapping()
    public String homePage() {
        return "home/main";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "authorization/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "authorization/registration";
    }


}
