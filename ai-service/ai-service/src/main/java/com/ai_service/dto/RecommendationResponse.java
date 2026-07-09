package com.ai_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendationResponse {
    private String recommendation;

    private List<String> improvement;

    private List<String> suggestions;

    private List<String> safety;
}
