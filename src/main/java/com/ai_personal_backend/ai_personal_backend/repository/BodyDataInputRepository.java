package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.stereotype.Repository;

import com.ai_personal_backend.ai_personal_backend.model.BodyData;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BodyDataInputRepository extends JpaRepository<BodyData, Long> {

}
