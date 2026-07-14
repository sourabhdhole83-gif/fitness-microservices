package com.fitness.user_service.repository;

import com.fitness.user_service.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Boolean existsByEmail(String email);


    Boolean existsByKeycloakId(UUID id);

    User findByEmail(String email);
}
