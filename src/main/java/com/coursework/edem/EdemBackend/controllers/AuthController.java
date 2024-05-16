package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.util.PersonValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final PersonService personService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "authorization/login_page";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "authorization/registration_page";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "authorization/registration_page";
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setCreatedAt(new Date());
        personService.save(person);
        return "redirect:/auth/login";
    }
}
