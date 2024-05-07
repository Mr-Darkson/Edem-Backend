package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Boolean hasAnyWithLogin(String login) {
        return personRepository.existsByLogin(login);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

}
