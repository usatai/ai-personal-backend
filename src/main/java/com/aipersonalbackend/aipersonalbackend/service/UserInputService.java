package com.aipersonalbackend.aipersonalbackend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipersonalbackend.aipersonalbackend.controller.SignupForm;
import com.aipersonalbackend.aipersonalbackend.model.Users;
import com.aipersonalbackend.aipersonalbackend.repository.UserInputRepository;
import com.aipersonalbackend.aipersonalbackend.security.PasswordUtil;

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
