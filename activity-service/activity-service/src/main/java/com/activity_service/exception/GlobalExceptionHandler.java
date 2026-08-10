package com.activity_service.exception;

import com.activity_service.dto.ErrorDto;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){

        ErrorDto errorDto= new ErrorDto(e.getMessage(),request.getDescription(false),404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }





    @ExceptionHandler(RateLimiterException.class)
    public ErrorDto rateLimiterException(RequestNotPermitted ex,WebRequest r){
        return new ErrorDto(ex.getMessage(),r.getDescription(false),429);
    }
}



