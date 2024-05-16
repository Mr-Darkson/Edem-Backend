package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.File;
import com.coursework.edem.EdemBackend.repositories.FileRepository;
import com.coursework.edem.EdemBackend.utils.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@AllArgsConstructor
public class FileUploadService {
    private final FileRepository fileRepository;

    public void uploadFilesToServer(MultipartFile[] multipartFile, Long messageId){
        String filePath = "C://Users/User/Desktop/edem/Edem-Backend/src/main/data/";
        // String filePath = "C://edem/Edem-Backend/src/main/data/"; // версия для вас
        for (int i=0;i<multipartFile.length;i++){
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
}
