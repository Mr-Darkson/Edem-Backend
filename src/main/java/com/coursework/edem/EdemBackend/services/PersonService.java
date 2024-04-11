package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonById(Integer id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = false)
    public void save(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setLastLogin(LocalDateTime.now());
        personRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void updateAvatar(Integer id, String newAvatar) {
        Person person = getPersonById(id);
        person.setAvatar(newAvatar);
        personRepository.save(person);

    }
}
