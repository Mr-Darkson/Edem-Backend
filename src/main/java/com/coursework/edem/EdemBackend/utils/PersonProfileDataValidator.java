package com.coursework.edem.EdemBackend.utils;

import com.coursework.edem.EdemBackend.models.AvatarFile;
import com.coursework.edem.EdemBackend.models.PersonProfileData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class PersonProfileDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AvatarFile.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonProfileData personProfileData = (PersonProfileData) target;

        String fileName = personProfileData.getAvatarFile().getOriginalFilename();
        String contentType = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!(contentType.equalsIgnoreCase("png")
                || contentType.equalsIgnoreCase("jpg")
                || contentType.equalsIgnoreCase("jpeg")
                || contentType.equalsIgnoreCase("webp"))) {
            errors.rejectValue("multipartFile", "", "Расширение изображения должно быть .png, .jpg, .jpeg или .webp");
        }
    }
}
