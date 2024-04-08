package com.coursework.edem.EdemBackend.repositories;

import com.coursework.edem.EdemBackend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
