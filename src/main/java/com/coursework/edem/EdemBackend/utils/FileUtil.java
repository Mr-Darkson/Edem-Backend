package com.coursework.edem.EdemBackend.utils;

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

    public static byte[] downloadFile(String path) throws IOException {
        FileInputStream in = new FileInputStream(path);
        return in.readAllBytes();
    }
}
