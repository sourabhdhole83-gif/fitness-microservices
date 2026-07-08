package com.ai_service.service;

import com.ai_service.entity.Recommendation;
import com.ai_service.exception.RecommendationNotFoundException;
import com.ai_service.repository.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final RecommendationRepo repository;


    public List<Recommendation> getUserRecommendation(UUID userId) {
        return repository.findByUserId(userId);

    }


    public Recommendation getActivityRecommendation(String activityId) {
       return repository.findByActivityId(activityId).orElseThrow(()->new RecommendationNotFoundException("No recommendation found for this activity "+activityId));

    }
}
