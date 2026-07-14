package com.fitness.user_service.controller;

import com.fitness.user_service.dto.RegisterRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> resgisterUser(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    @GetMapping("/{id}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable UUID id){
        return ResponseEntity.ok(userService.validateUserById(id));
    }
}
