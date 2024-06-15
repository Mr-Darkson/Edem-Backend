package com.coursework.edem.EdemBackend.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    // Инструменты для загрузки файлов
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void uploadMutlipleFiles(MultipartFile[] multipartFile, String filePath, String fileName) throws IOException {
        ZipOutputStream archive = new ZipOutputStream(new FileOutputStream(filePath + fileName));
        for (int i = 0; i < multipartFile.length; i++) {
            ZipEntry file = new ZipEntry(multipartFile[i].getOriginalFilename());
            archive.putNextEntry(file);
            archive.write(multipartFile[i].getBytes());
            archive.closeEntry();
        }
        archive.close();
    }

    public static void downloadFile(String name, HttpServletResponse response) throws IOException {
        String dirPath = System.getProperty("user.dir") + "/src/main/data/" + name;

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + name);

        InputStream inputStream = new FileInputStream(dirPath);
        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }

    public static void deleteFile(String name){
        String pathToFile = System.getProperty("user.dir") + "/src/main/data/" + name;
        File file = new File(pathToFile);
        if (file.exists() && file.isFile()){
            try{
                file.delete();
            }
            catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }
}
