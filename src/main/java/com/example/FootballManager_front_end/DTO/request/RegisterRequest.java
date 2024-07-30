package com.example.FootballManager_front_end.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Full name cannot be empty!")
    @Length(min = 2, message = "Full name should be at least than 2 characters long!")
    private String fullName;

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Please enter a valid email address!")
    private String email;

    @NotBlank(message = "Username cannot be empty!")
    @Length(min=3, max = 10, message = "Username should be from 3 to 10 characters long!")
    private String username;

    @NotBlank(message = "Password cannot be empty!")
    @Length(min = 8, message = "Password should be at least 8 characters long!")
    private String password;
}
