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
import java.util.UUID;


@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void uploadFilesToServer(MultipartFile[] multipartFile, Long messageId) {
        String filePath = "C://Users/User/Desktop/edem/Edem-Backend/src/main/data/";
        // String filePath = "C://edem/Edem-Backend/src/main/data/"; // версия для вас
        if (multipartFile.length == 1 && multipartFile[0].isEmpty()) return;
        for (int i = 0; i < multipartFile.length; i++) {
            String fileName = UUID.randomUUID().toString() + multipartFile[i].getOriginalFilename();
            while (fileRepository.findByFilename(fileName).isPresent()) {
                fileName = UUID.randomUUID().toString() + multipartFile[i].getOriginalFilename();
            }
            try {
                FileUtil.uploadFile(multipartFile[i].getBytes(), filePath, fileName);
                fileRepository.save(new File(messageId, fileName));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void downloadFilesFromServer(Long messageId, HttpServletResponse response) {
        List<File> files = fileRepository.findAllByMessageId(messageId);

        for (int i = 0; i < files.size(); i++) {
            try{
                FileUtil.downloadFile(files.get(i).getFilename(), response);
            } catch (IOException e){
                System.out.println("Error filename!");
            }
        }
    }
}
