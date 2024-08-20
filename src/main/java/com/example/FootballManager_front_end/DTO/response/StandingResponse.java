package com.example.FootballManager_front_end.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StandingResponse {
    private Long leagueId;
    private String leagueName;
    private String teamName;
    private int playedMatches;
    private int scoredGoals;
    private int concededGoals;
    private int points;
}
