package com.coursework.edem.EdemBackend.repositories;

import com.coursework.edem.EdemBackend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByLogin(String username);

    Boolean existsByLogin(String login);

}
