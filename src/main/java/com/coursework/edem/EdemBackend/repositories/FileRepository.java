package com.coursework.edem.EdemBackend.repositories;

import com.coursework.edem.EdemBackend.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
}
