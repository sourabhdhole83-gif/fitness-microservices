package com.fitness.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDto {

    private String message;
    private String path;
    private int status;
    private LocalDateTime timeStamp;

    public ErrorDto(String message,String path,int status,LocalDateTime timeStamp){
        this.message = message;
        this.path = path;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
