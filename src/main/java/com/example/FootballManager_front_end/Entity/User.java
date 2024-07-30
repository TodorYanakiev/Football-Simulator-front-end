package com.example.FootballManager_front_end.Entity;

import com.example.FootballManager_front_end.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String password;
    private String username;
    private String email;
    private Role role;
}
