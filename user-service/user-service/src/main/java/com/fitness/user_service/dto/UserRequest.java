package com.fitness.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequest {

    @Email(message = "Wrong email format")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$")
    private String password;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
