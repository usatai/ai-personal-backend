package com.ai_personal_backend.ai_personal_backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.controller.LoginForm;
import com.ai_personal_backend.ai_personal_backend.model.Users;
import com.ai_personal_backend.ai_personal_backend.repository.UserInputRepository;
import com.ai_personal_backend.ai_personal_backend.security.PasswordUtil;
import com.ai_personal_backend.ai_personal_backend.exception.*;

@Service
public class UserLoginService {

    @Autowired
    UserInputRepository userInputRepository;

    public long login(LoginForm loginForm) {

        Optional<Users> optionalUser = userInputRepository.findByUserName(loginForm.userName());

        if (optionalUser.isEmpty()) {
            throw new UsersException("認証失敗");
        }

        Users user = optionalUser.get();

        if (!PasswordUtil.matchesPassword(loginForm.userPassword(), user.getUserPassword())) {
            throw new UsersException("認証失敗");
        }

        return user.getId();
    }

    public LocalDateTime getCreatedAt(Long userId) {
        return userInputRepository.getCreatedAt(userId);
    }

}
