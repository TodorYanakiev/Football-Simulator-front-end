package com.example.FootballManager_front_end.DTO;

import com.example.FootballManager_front_end.Enum.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private Role role;
}
