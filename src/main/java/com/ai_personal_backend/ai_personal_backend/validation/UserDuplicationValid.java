package com.ai_personal_backend.ai_personal_backend.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai_personal_backend.ai_personal_backend.repository.UserInputRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UserDuplicationValid implements ConstraintValidator<UserDuplication,String>{

    @Autowired
    UserInputRepository userInputRepository;

    @Override
    public void initialize(UserDuplication userDuplication) {

    }

    @Override
    public boolean isValid(String userName,ConstraintValidatorContext context) {
        if (userName == null || userName.isEmpty()) {
            return true;
        }

        Long userCount = userInputRepository.existsByUser(userName);
        if (userCount == 1) {
            return false;
        }

        return true;
    }
}
