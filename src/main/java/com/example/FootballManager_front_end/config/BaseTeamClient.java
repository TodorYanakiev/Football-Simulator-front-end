package com.example.FootballManager_front_end.config;

import com.example.FootballManager_front_end.DTO.BaseTeamDTO;
import com.example.FootballManager_front_end.Entity.BaseTeam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "footballSimulator-api-auth", url = "${backend-service.url}/api/v1/base-teams")
public interface BaseTeamClient {
    @PostMapping
    ResponseEntity<BaseTeamDTO> createBaseTeam(@RequestHeader("Authorization") String token
            , @Valid @RequestBody BaseTeamDTO baseTeamDTO);

    @GetMapping
    ResponseEntity<List<BaseTeamDTO>> getAllBaseTeams(@RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    ResponseEntity<BaseTeamDTO> getBaseTeamById(@RequestHeader("Authorization") String token, @PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity<BaseTeamDTO> updateBaseTeamById(@RequestHeader("Authorization") String token, @PathVariable Long id
            , @Valid @RequestBody BaseTeamDTO newBaseTeamDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBaseTeamById(@RequestHeader("Authorization") String token, @PathVariable Long id);
}
