package com.fitness.user_service.service;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.entity.User;
import com.fitness.user_service.exception.UserNotFoundException;
import com.fitness.user_service.mapper.UserMapper;
import com.fitness.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private UserMapper mapper;
    private PasswordEncoder encoder;

    public UserResponse register(UserRequest request) {

        if(repository.existsByEmail()==false){
            throw new UserNotFoundException("User not found with email: "+request.getEmail());
        }

        User user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        UserResponse response = mapper.toResponse(savedUser);

        return response;
    }
}
