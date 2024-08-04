package com.example.FootballManager_front_end.DTO;

import com.example.FootballManager_front_end.Enum.Role;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private Role role;
}
