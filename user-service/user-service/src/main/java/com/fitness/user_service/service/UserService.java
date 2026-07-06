package com.fitness.user_service.service;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;

public interface UserService {
    public UserResponse register(UserRequest request);
}
