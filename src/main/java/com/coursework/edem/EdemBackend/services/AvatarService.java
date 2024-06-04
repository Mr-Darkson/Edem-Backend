package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.AvatarRepository;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private PersonRepository personRepository;

    public File getAvatarByPersonId(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        Avatar avatar = avatarRepository.findAvatarByPerson(person).orElse(null);

        String filePath = System.getProperty("user.dir") + "/src/main/";
        if (avatar == null) {
            filePath = filePath + "resources/static/img/sms/Ellipse.png";
        } else {
            filePath = filePath + "avatars/" + avatar.getAvatarName();
        }

        return new File(filePath);
    }
}
