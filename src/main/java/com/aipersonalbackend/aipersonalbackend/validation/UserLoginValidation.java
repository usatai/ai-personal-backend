package com.aipersonalbackend.aipersonalbackend.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aipersonalbackend.aipersonalbackend.controller.LoginForm;
import com.aipersonalbackend.aipersonalbackend.model.Users;
import com.aipersonalbackend.aipersonalbackend.repository.UserInputRepository;
import com.aipersonalbackend.aipersonalbackend.security.PasswordUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserLoginValidation implements ConstraintValidator<UserLogin,LoginForm>{
    @Autowired
    UserInputRepository userInputRepository;

    @Override
    public void initialize(UserLogin userLogin) {

    }

    @Override
    public boolean isValid(LoginForm loginForm,ConstraintValidatorContext context) {
        if (loginForm.userName() == null || loginForm.userName().isEmpty() || loginForm.userPassword() == null
        || loginForm.userPassword().isEmpty()) {
            return true;
        }

        Optional<Users> dbUser = userInputRepository.findByUserName(loginForm.userName());

        if (dbUser.isEmpty() || !PasswordUtil.matchesPassword(loginForm.userPassword(), dbUser.get().getUserPassword())) {
            // デフォルトのメッセージを無効化
            context.disableDefaultConstraintViolation();
    
            // userName にエラーを付与
            context.buildConstraintViolationWithTemplate("ユーザー名またはパスワードが間違っています")
                    .addPropertyNode("userName")
                    .addConstraintViolation();
    
            return false;
        }

        return true;
    }
    
}
