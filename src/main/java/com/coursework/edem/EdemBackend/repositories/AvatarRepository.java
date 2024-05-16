package com.coursework.edem.EdemBackend.repositories;

import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByPerson(Person person);
}
