package com.gateway.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$"
    ,message =  "Password must be atlest 8 charachters and must have a capital and special symbol ")
    private String password;

    private String keycloakId;
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
