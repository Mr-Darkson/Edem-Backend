package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.models.PersonProfileData;
import com.coursework.edem.EdemBackend.repositories.AvatarRepository;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarService avatarService;
    private PasswordEncoder passwordEncoder;

    public Boolean hasAnyWithLogin(String login) {
        return personRepository.existsByLogin(login);
    }

    public Optional<Person> getPersonByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = false)
    public void deleteAvatar(Avatar avatar) {
        String oldAvatarName = avatar.getAvatarName();
        String oldAvatarPath = System.getProperty("user.dir") + "/src/main/avatars/" + oldAvatarName;
        File avatarToDelete = new File(oldAvatarPath);

        avatarToDelete.delete();
    }


    @Transactional(readOnly = false)
    public void updateAvatar(Long id, String newAvatar) {
        Person person = getPersonById(id);

        if (person.getAvatar() == null) {
            Avatar avatar = new Avatar(person, newAvatar);

            person.setAvatar(avatar);
            personRepository.save(person);
        } else {
            Avatar avatar = person.getAvatar();

            deleteAvatar(avatar);

            avatar.setAvatarName(newAvatar);
            personRepository.save(person);
        }
    }

    @Transactional(readOnly = false)
    public void updatePerson(Long id, PersonProfileData personProfileData) {
        Person person = getPersonById(id);

        String newUsername = personProfileData.getUsername();
        MultipartFile newAvatar = personProfileData.getAvatarFile();
        String newPassword = personProfileData.getPassword();

        if (!newUsername.isEmpty()) {
            person.setUsername(newUsername);
        }

        if (!newAvatar.isEmpty()) {
            String uuidFile = UUID.randomUUID().toString();
            String fileName = uuidFile + "." + newAvatar.getOriginalFilename();
            String filePath = System.getProperty("user.dir") + "/src/main/avatars/";

            try {
                FileUtil.uploadFile(newAvatar.getBytes(), filePath, fileName);

                updateAvatar(id, fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (!newPassword.isEmpty()) {
            person.setPassword(passwordEncoder.encode(newPassword));
        }

        personRepository.save(person);
    }
}
