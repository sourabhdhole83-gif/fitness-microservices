package com.fitness.user_service.exception;

import com.fitness.user_service.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> exception(UserNotFoundException exception, WebRequest request){

        ErrorDto errorDto = new ErrorDto(exception.getMessage(),
                                         request.getDescription(false),
                                         404, LocalDateTime.now());

        return ResponseEntity.status(404).body(errorDto);
    }
}
