package com.example.FootballManager_front_end.config;

import com.example.FootballManager_front_end.DTO.UserDTO;
import com.example.FootballManager_front_end.DTO.request.AuthenticationRequest;
import com.example.FootballManager_front_end.DTO.request.RegisterRequest;
import com.example.FootballManager_front_end.DTO.response.AuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "footballSimulator-api-auth", url = "${backend-service.url}/api/v1/auth")
public interface AuthClient {
    @PostMapping("/register")
    AuthenticationResponse register(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

    @GetMapping("/get-info")
    ResponseEntity<UserDTO> getUserInfo(@RequestHeader("Authorization") String token);

    @GetMapping("/logout")
    void logout(@RequestHeader("Authorization") String auth);
}
