package com.coursework.edem.EdemBackend.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Component
public class FileValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return MultipartFile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MultipartFile file = (MultipartFile) target;

        String fileName = file.getOriginalFilename();
        String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);

    }
}
