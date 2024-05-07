package com.coursework.edem.EdemBackend.util;

import com.coursework.edem.EdemBackend.models.Person;
import com.coursework.edem.EdemBackend.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class PersonValidator implements Validator {
    private final PersonService personService;
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(!hasCorrectLogin(person)) {
            errors
                    .rejectValue("login", " ",
                            "Имя должно быть длинной от 3 до 30 символов," +
                                    " а так же может содержать только заглавные/строчные латинские буквы" +
                                    " и символы -, _,(, ).");
        }
        if(personService.hasAnyWithLogin(person.getLogin())) {
            errors
                    .rejectValue("login", " ",
                            "Данное имя пользователя занято.");
        }

    }

    private static boolean hasCorrectLogin(Person person){
        String login = person.getLogin();
        if(login.length() >= 3 && login.length() <= 30) {
            if(login.matches("^[a-zA-Z0-9-_)(]*$")) {
                return true;
            }
        }
        return false;
    }

}
