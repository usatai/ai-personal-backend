package com.aipersonalbackend.aipersonalbackend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserLoginValidation.class)
public @interface UserLogin {
    String message() default "入力したユーザー名・パスワードが間違っています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
