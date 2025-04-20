package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.SignupForm;
import com.ai_personal_backend.ai_personal_backend.model.Users;
import com.ai_personal_backend.ai_personal_backend.repository.UserInputRepository;
import com.ai_personal_backend.ai_personal_backend.security.PasswordUtil;

@Service
public class UserInputService {

    @Autowired
    UserInputRepository userInputRepository;

    private Users createUser(SignupForm signupForm) {
        Users users = new Users();
        LocalDateTime now = LocalDateTime.now();
        String hashedPassword = PasswordUtil.encodePassword(signupForm.userPassword());

        users.setUserName(signupForm.userName());
        users.setUserEmail(signupForm.userEmail());
        users.setUserPassword(hashedPassword);
        users.setCreatedAt(now);

        return users;
    }

    public void userInput(SignupForm signupForm) {
        userInputRepository.save(createUser(signupForm));
    }

    public Long getUserId(SignupForm signupForm) {
        return userInputRepository.getUserId(signupForm.userName(), signupForm.userEmail());
    }

}
