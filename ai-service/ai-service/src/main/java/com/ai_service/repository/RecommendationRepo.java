package com.ai_service.repository;

import com.ai_service.entity.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRepo extends MongoRepository<Recommendation,String> {

    List<Recommendation> findByUserId(UUID userId);

    Optional<Recommendation> findByActivityId(String activityId);
}
