package com.activity_service.exception;

public class RateLimiterException extends RuntimeException{

    public RateLimiterException(String message){
        super(message);
    }
}
