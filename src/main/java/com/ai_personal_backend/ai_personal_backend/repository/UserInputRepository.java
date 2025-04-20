package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.Users;
import java.util.Optional;

@Repository
public interface UserInputRepository extends JpaRepository<Users, Long> {

    // 新規登録時にユーザーのIDを取得
    @Query(value = "SELECT id FROM users WHERE user_name = :userName AND user_email = :userEmail", nativeQuery = true)
    Long getUserId(@Param("userName") String userName, @Param("userEmail") String userEmail);

    // ログイン時に該当ユーザーのパスワードを取得
    Optional<Users> findByUserName(String userName);
}
