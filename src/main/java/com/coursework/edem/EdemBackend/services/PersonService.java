package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.AvatarRepository;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarService avatarService;

    public Boolean hasAnyWithLogin(String login) {
        return personRepository.existsByLogin(login);
    }

    public Optional<Person> getPersonByLogin(String login){ return personRepository.findByLogin(login);}

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public void deleteAvatar(Long personId) {
        Person person = personRepository.findById(personId).orElse(null);

        if (person.getAvatar() != null) {
            Avatar avatar = avatarRepository.findAvatarByPerson(person).orElse(null);
            person.setAvatar(null);

            avatar.setPerson(null);

            personRepository.save(person);
            avatarRepository.delete(avatar);
        }
    }


    @Transactional(readOnly = false)
    public void updateAvatar(Long id, String newAvatar) {
        Person person = getPersonById(id);
        //deleteAvatar(id);
        if (person.getAvatar() == null) {
            Avatar avatar = new Avatar(person, newAvatar);

            person.setAvatar(avatar);
            personRepository.save(person);
        } else {
            Avatar avatar = person.getAvatar();
            avatar.setAvatarName(newAvatar);
            personRepository.save(person);
        }
    }

    @Transactional(readOnly = false)
    public void updatePerson(Long id, MultipartFile newAvatar, String nickname) {
        Person person = getPersonById(id);

        if (!nickname.isEmpty()) {
            person.setUsername(nickname);
        }

        if (!newAvatar.isEmpty()) {
            String fileName = newAvatar.getOriginalFilename();
            // String filePath = "C://edem/Edem-Backend/src/main/avatars/"; // ваша версия пути
            String filePath = "C://Users/User/Desktop/edem/Edem-Backend/src/main/avatars/";
            try {
                FileUtil.uploadFile(newAvatar.getBytes(), filePath, fileName);

                updateAvatar(id, fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        personRepository.save(person);
    }
}
