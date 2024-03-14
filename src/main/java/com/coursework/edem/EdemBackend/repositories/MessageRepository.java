package com.coursework.edem.EdemBackend.repositories;


import com.coursework.edem.EdemBackend.models.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
