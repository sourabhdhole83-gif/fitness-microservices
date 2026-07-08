package com.activity_service.exception;

import com.activity_service.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
}
