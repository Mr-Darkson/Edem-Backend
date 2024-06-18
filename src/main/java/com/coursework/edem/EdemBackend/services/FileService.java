package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.File;
import com.coursework.edem.EdemBackend.repositories.FileRepository;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.coursework.edem.EdemBackend.utils.FileUtil.deleteFile;


@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void uploadFilesToServer(MultipartFile[] multipartFile, Long messageId) {
        String filePath = System.getProperty("user.dir") + "/data/";
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

    public void deleteFilesFromServerByIds(List<Long> messageIds){
        for (Long messageId : messageIds){
            deleteFilesFromServer(messageId);
        }
    }
    public void deleteFilesFromServer(Long messageId){
        var fileData = fileRepository.findByMessageId(messageId);
        fileData.ifPresent(file -> deleteFile(file.getFilename()));
    }

    public boolean isAnyFiles(Long messageId){
        Optional<File> files = fileRepository.findByMessageId(messageId);
        return files.isPresent();
    }

    public void saveFileWithName (String fileName, Long messageId) {
        fileRepository.save(new File(messageId, fileName));
    }

    public Optional<File> findFileByMessageId(Long messageId) {
        return fileRepository.findByMessageId(messageId);
    }
}
