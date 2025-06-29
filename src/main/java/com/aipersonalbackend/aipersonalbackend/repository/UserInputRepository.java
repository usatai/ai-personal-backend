package com.aipersonalbackend.aipersonalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aipersonalbackend.aipersonalbackend.model.Users;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserInputRepository extends JpaRepository<Users, Long> {

    // 新規登録時にユーザーのIDを取得
    @Query(value = "SELECT id FROM users WHERE user_name = :userName AND user_email = :userEmail", nativeQuery = true)
    Long getUserId(@Param("userName") String userName, @Param("userEmail") String userEmail);

    // ログイン時に該当ユーザーのパスワードを取得
    Optional<Users> findByUserName(String userName);

    @Query(value = "SELECT created_at FROM users WHERE id = :userId", nativeQuery = true)
    public LocalDateTime getCreatedAt(@Param("userId") Long userId);

    //同名のユーザーの場合1(TRUE)を返す
    @Query(value="SELECT EXISTS(SELECT 1 FROM users WHERE user_name = :userName)", nativeQuery = true)
    Long existsByUser(@Param("userName") String userName);
}
