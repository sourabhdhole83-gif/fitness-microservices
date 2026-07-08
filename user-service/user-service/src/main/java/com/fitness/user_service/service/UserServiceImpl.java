package com.fitness.user_service.service;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.entity.User;
import com.fitness.user_service.exception.ResourceNotFoundException;
import com.fitness.user_service.exception.UserNotFoundException;
import com.fitness.user_service.mapper.UserMapper;
import com.fitness.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private UserMapper mapper;
    private PasswordEncoder encoder;

    public UserResponse register(UserRequest request) {

        if(repository.existsByEmail(request.getEmail())==true){
            throw new UserNotFoundException("User already exists with email: "+request.getEmail());
        }

        User user = mapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));

        User savedUser = repository.save(user);

        UserResponse response = mapper.toResponse(savedUser);

        return response;
    }

    public UserResponse getUserProfile(UUID id){
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));

        UserResponse response = mapper.toResponse(user);

        return response;
    }

    @Override
    public Boolean validateUserById(UUID id) {
        return repository.existsById(id);
    }
}
