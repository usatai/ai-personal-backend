package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.Users;

@Repository
public interface UserInputRepository extends JpaRepository<Users, Long> {

    // 新規登録時にユーザーのIDを取得
    @Query(value = "SELECT id FROM users WHERE user_name = :user_name AND user_email = :user_email", nativeQuery = true)
    Long getUserId(@Param("user_name") String user_name, @Param("user_email") String user_email);

    // ログインチェッククエリ
    @Query(value = "SELECT id FROM users WHERE user_name = :user_name AND user_password = :user_password", nativeQuery = true)
    Long login(@Param("user_name") String user_name, @Param("user_password") String user_password);

}
