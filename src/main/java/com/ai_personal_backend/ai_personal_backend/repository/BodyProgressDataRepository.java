package com.ai_personal_backend.ai_personal_backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ai_personal_backend.ai_personal_backend.model.BodyProgressData;

@Repository
public interface BodyProgressDataRepository extends JpaRepository<BodyProgressData, Long> {

}
