package com.aipersonalbackend.aipersonalbackend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipersonalbackend.aipersonalbackend.controller.LoginForm;
import com.aipersonalbackend.aipersonalbackend.exception.*;
import com.aipersonalbackend.aipersonalbackend.model.Users;
import com.aipersonalbackend.aipersonalbackend.repository.UserInputRepository;
import com.aipersonalbackend.aipersonalbackend.security.PasswordUtil;

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
