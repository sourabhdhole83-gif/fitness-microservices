package com.fitness.user_service.service;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;

import java.util.UUID;

public interface UserService {
     UserResponse register(UserRequest request);

    UserResponse getUserProfile(UUID id);

    Boolean validateUserById(UUID id);
}
