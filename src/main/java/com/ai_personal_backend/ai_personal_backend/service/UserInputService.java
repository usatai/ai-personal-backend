package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.SignupForm;
import com.ai_personal_backend.ai_personal_backend.model.Users;
import com.ai_personal_backend.ai_personal_backend.repository.UserInputRepository;

@Service
public class UserInputService {

    @Autowired
    UserInputRepository userInputRepository;

    private Users createUser(SignupForm signupForm) {
        Users users = new Users();
        LocalDateTime now = LocalDateTime.now();

        users.setUser_name(signupForm.user_name());
        users.setUser_email(signupForm.user_email());
        users.setUser_password(signupForm.user_password());
        users.setCreated_at(now);

        return users;
    }

    public void userInput(SignupForm signupForm) {
        userInputRepository.save(createUser(signupForm));
    }

    public Long getUserId(SignupForm signupForm) {
        return userInputRepository.getUserId(signupForm.user_name(), signupForm.user_email());
    }

}
