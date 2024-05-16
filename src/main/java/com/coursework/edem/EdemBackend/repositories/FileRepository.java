package com.coursework.edem.EdemBackend.repositories;

import com.coursework.edem.EdemBackend.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByFilename(String filename);

    List<File> findAllByMessageId(Long messageId);
}
