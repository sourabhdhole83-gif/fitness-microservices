package com.ai_service.dto.GeminiResponseDtos;

import lombok.Data;

import java.util.List;

@Data
public class Step {
    private String type;

    private List<Content> content;
}
