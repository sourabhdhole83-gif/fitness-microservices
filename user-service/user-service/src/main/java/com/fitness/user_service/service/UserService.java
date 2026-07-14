package com.fitness.user_service.service;

import com.fitness.user_service.dto.RegisterRequest;
import com.fitness.user_service.dto.UserResponse;

import java.util.UUID;

public interface UserService {
     UserResponse register(RegisterRequest request);

    UserResponse getUserProfile(UUID id);

    Boolean validateUserById(String id);
}
