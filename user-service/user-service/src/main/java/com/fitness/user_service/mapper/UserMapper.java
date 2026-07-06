package com.fitness.user_service.mapper;

import com.fitness.user_service.dto.UserRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);
    User toEntity(UserRequest request);

}
