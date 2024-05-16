package com.coursework.edem.EdemBackend.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;

public class FileUtil {
    // Инструменты для загрузки файлов
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
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
}
