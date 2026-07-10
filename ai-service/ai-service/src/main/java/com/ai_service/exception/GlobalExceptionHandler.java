package com.ai_service.exception;

import com.ai_service.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecommendationNotFoundException.class)
    public ResponseEntity<ErrorDto> recommendationNotFoundException(RecommendationNotFoundException exception, WebRequest request){
        ErrorDto errorDto = new ErrorDto(exception.getMessage(),request.getDescription(false),404, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(AIServiceException.class)
    public ResponseEntity<ErrorDto> aiServiceException(AIServiceException exception,WebRequest request){
        ErrorDto errorDto = new ErrorDto(exception.getMessage(),request.getDescription(false),500,LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }
}
