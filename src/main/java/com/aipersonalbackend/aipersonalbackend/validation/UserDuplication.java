package com.aipersonalbackend.aipersonalbackend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserDuplicationValid.class)
public @interface UserDuplication {
    String message() default "入力したユーザー名はすでに登録されています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
 }
