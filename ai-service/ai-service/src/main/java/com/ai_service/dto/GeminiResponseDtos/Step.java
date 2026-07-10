package com.ai_service.dto.GeminiResponseDtos;

import lombok.Data;

import java.util.List;

@Data
public class Step {

    private String type;

    private String signature;

    private List<Content> content;
}
