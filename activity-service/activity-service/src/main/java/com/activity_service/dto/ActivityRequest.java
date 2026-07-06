package com.activity_service.dto;

import com.activity_service.entity.ActivityType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequest {
    private String userId;

    private ActivityType type;

    private Integer duration;

    private Integer caloriesBurned;

    private LocalDateTime startTime;

    private Map<String, Object> additionalMetrics;
}
