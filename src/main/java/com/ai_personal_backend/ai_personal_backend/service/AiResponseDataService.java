package com.ai_personal_backend.ai_personal_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ai_personal_backend.ai_personal_backend.model.AiResponseData;
import com.ai_personal_backend.ai_personal_backend.repository.AiResponseDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiResponseDataService {

    private final AiResponseDataRepository aiResponseDataRepository;

    public Optional<AiResponseData> getAiResponseDataByUserId(Long userId) {
        return aiResponseDataRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }
} 