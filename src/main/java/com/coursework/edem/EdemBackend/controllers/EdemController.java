package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import com.coursework.edem.EdemBackend.security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()

public class EdemController {
    @GetMapping("/")
    public String homePage( Model model) {
        return "home/index";
    }


}
