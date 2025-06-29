package com.aipersonalbackend.aipersonalbackend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aipersonalbackend.aipersonalbackend.model.AiResponseData;
import com.aipersonalbackend.aipersonalbackend.repository.AiResponseDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiResponseDataService {

    private final AiResponseDataRepository aiResponseDataRepository;

    public Optional<AiResponseData> getAiResponseDataByUserId(Long userId) {
        return aiResponseDataRepository.findFirstByUserIdOrderByCreatedAtDesc(userId);
    }
} 