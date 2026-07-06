package com.fitness.user_service.exception;

import com.fitness.user_service.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> exception(UserNotFoundException exception, WebRequest request){

        ErrorDto errorDto = new ErrorDto(exception.getMessage(),
                                         request.getDescription(false),
                                         404, LocalDateTime.now());

        return ResponseEntity.status(404).body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> MethodArgumentNotValidException(MethodArgumentNotValidException exception){

        Map<String, String> errors =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                FieldError::getDefaultMessage
                        ));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> resourceNotFoundException(ResourceNotFoundException exception,WebRequest request){
        ErrorDto dto = new ErrorDto(exception.getMessage(),request.getDescription(false),
                                    404,LocalDateTime.now()) ;
        return ResponseEntity.status(404).body(dto);
    }






}
