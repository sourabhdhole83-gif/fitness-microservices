package com.fitness.user_service.controller;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserServiceImpl userService;


    @PostMapping("/resgister")
    public ResponseEntity<UserResponse> resgisterUser(@RequestBody UserRequest request){
        return ResponseEntity.ok(userService.register(request));
    }
}
