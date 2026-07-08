package com.ai_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDto {
    private String message;
    private String path;
    private int status;
    private LocalDateTime localDateTime;


}
