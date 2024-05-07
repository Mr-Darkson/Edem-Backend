package com.coursework.edem.EdemBackend.repositories;


import com.coursework.edem.EdemBackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>  {

}
