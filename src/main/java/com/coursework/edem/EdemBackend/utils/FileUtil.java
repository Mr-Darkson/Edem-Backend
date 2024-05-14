package com.coursework.edem.EdemBackend.utils;

import java.io.File;
import java.io.FileOutputStream;

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
}