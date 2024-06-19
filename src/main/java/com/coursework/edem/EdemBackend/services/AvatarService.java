package com.coursework.edem.EdemBackend.services;

import com.coursework.edem.EdemBackend.models.Avatar;
import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.repositories.AvatarRepository;
import com.coursework.edem.EdemBackend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Service
public class AvatarService {
    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private PersonRepository personRepository;

    public File getAvatarByPersonId(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        Avatar avatar = avatarRepository.findAvatarByPerson(person).orElse(null);

        String filePath;
        if (avatar == null) {
            filePath = System.getProperty("user.dir") + "/img/sms/Ellipse.png"; //В докере путь будет такой /app->img->sms->Ellipse.png
            if(!new File(filePath).exists()) {
                filePath = findPathToFile(System.getProperty("user.dir"), "Ellipse.png");
            }
            if(filePath == null) return new File("");
        } else {
            filePath = System.getProperty("user.dir") + "/src/main/avatars/" + avatar.getAvatarName();
        }


        return new File(filePath);
    }


    // path - Стартовая дирректория поиска, fileToFound - Название файла, который ищем (Обяз с расширением)
    public static String findPathToFile(String path, String fileToFind) {
        File startFile = new File(path);
        System.out.println("Root dir to search = " + startFile.getAbsolutePath());
        Queue<File> que = new LinkedList<>();
        for(File file : Objects.requireNonNull(startFile.listFiles())) {
            //System.out.println(file.getAbsolutePath()); log
            if(file.getAbsolutePath().endsWith(fileToFind)) {
                System.out.println(file.getAbsolutePath() + "is FOUND!");
                return file.getAbsolutePath();
            }
            if(file.isDirectory() &&
                    !file.getName().endsWith(".git") &&
                    !file.getName().endsWith(".idea") &&
                    !file.getName().endsWith(".mvn") &&
                    !file.getName().endsWith("src")) que.add(file);
        }
        while (!que.isEmpty()) {
            File taken = que.poll();
            for(File file : taken.listFiles()) {
                //System.out.println(file.getAbsolutePath()); log
                if(file.getAbsolutePath().endsWith(fileToFind)) {
                    System.out.println(file.getAbsolutePath() + " is FOUND!");
                    return file.getAbsolutePath();
                }
                if(file.isDirectory() && !file.getName().endsWith("test")) que.add(file);
            }
        }
        return null;
    }

}


