package com.ai_personal_backend.ai_personal_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.LoginForm;
import com.ai_personal_backend.ai_personal_backend.repository.UserInputRepository;

@Service
public class UserLoginService {

    @Autowired
    UserInputRepository userInputRepository;

    public long login(LoginForm loginForm) {
        return userInputRepository.login(loginForm.user_name(), loginForm.user_password());
    }

}
