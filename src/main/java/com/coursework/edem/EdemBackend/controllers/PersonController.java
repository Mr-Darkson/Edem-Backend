package com.coursework.edem.EdemBackend.controllers;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.services.PersonService;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable("id") Long id, Model model) {
        Person person = personService.getPersonById(id);
        model.addAttribute("person", person);
        return "profile/show";
    }



    @GetMapping("profile/new")
    public String newProfile(@ModelAttribute("person") Person person) {
        return "profile/new";
    }

    @PostMapping("/profile")
    public String createProfile(@ModelAttribute("person") Person person) {
        personService.save(person);
        return "redirect:/";
    }

    @GetMapping("/profile/{id}/change_avatar")
    public String changeAvatar(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "profile/change_avatar";
    }

    @PostMapping("/profile/{id}/change_avatar")
    public String uploadImg(@RequestParam("file") MultipartFile file, Model model, @PathVariable("id") Long id) {
        // Если картинка пуста, вывести ошибку
        if ((file.getOriginalFilename().isEmpty())) {
            model.addAttribute("error", "Выберите изображение!");
            model.addAttribute("id", id);
            return "/profile/change_avatar";
        } else {
            //Head head = new Head();
            String fileName = file.getOriginalFilename();
            String filePath = "C://edem/Edem-Backend/src/main/avatars/"; //путь для сохранения
            String DBFilePath = fileName; // путь для бд
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);

                personService.updateAvatar(id, DBFilePath);

            } catch (Exception e) {

            }
            return "redirect:/profile/" + id;
        }
    }
}
