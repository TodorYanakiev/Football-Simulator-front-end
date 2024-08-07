package com.example.FootballManager_front_end.config;

import com.example.FootballManager_front_end.DTO.BaseFootballPlayerDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "footballSimulator-api-base-player", url = "${backend-service.url}/api/v1/base-football-players")
public interface BasePlayerClient {
    @PostMapping
    ResponseEntity<BaseFootballPlayerDTO> createBaseFootballPlayer(@RequestHeader("Authorization") String token, @Valid @RequestBody BaseFootballPlayerDTO baseFootballPlayerDTO);

    @GetMapping
    ResponseEntity<List<BaseFootballPlayerDTO>> getAllFootballPlayers(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<BaseFootballPlayerDTO> getBaseFootballPlayerById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<BaseFootballPlayerDTO> updateBaseFootballPlayerById(@RequestHeader("Authorization") String token, @PathVariable Long id, @Valid @RequestBody BaseFootballPlayerDTO newBaseFootballPlayerDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBaseFootballPlayerById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
