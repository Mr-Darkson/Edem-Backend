package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.File;
import com.coursework.edem.EdemBackend.repositories.FileRepository;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void uploadFilesToServer(MultipartFile[] multipartFile, Long messageId) {
        String filePath = System.getProperty("user.dir") + "/src/main/data/";
        if (multipartFile.length == 1 && multipartFile[0].isEmpty()) return;
        String fileName = UUID.randomUUID().toString() + ".zip";
        while (fileRepository.findByFilename(fileName).isPresent()) {
            fileName = UUID.randomUUID().toString() + ".zip";
        }
        try {
            FileUtil.uploadMutlipleFiles(multipartFile, filePath, fileName);
            fileRepository.save(new File(messageId, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadFilesFromServer(Long messageId, HttpServletResponse response) {
        Optional<File> files = fileRepository.findByMessageId(messageId);
        if (files.isPresent()) {
            try {
                FileUtil.downloadFile(files.get().getFilename(), response);
            } catch (IOException e) {
                System.out.println("Error filename!");
            }
        }
    }
}
