package com.ai_service.dto.GeminiResponseDtos;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponse {

    private String id;

    private String status;

    private List<Step> steps;
}