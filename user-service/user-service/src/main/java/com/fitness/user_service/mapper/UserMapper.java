package com.fitness.user_service.mapper;

import com.fitness.user_service.dto.RegisterRequest;
import com.fitness.user_service.dto.UserResponse;
import com.fitness.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "firstName",target="firstName")
    UserResponse toResponse(User user);
    User toEntity(RegisterRequest request);

}
