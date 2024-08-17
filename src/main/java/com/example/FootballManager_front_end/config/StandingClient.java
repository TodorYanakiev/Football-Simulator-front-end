package com.example.FootballManager_front_end.config;

import com.example.FootballManager_front_end.DTO.response.StandingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "footballSimulator-api-standing", url = "${backend-service.url}/api/v1/standings")
public interface StandingClient {
    @GetMapping("/league/{leagueId}")
    List<StandingResponse> getStandingsForLeague(@PathVariable("leagueId") Long leagueId);
}
