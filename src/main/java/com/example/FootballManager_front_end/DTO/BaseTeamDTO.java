package com.example.FootballManager_front_end.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseTeamDTO {
    private Long id;
    @NotEmpty
    @Size(min = 3)
    private String name;
    @NotEmpty
    @Size(min = 2, max = 4)
    private String abbreviation;
    @NotEmpty
    @Size(min = 3)
    private String stadiumName;
    @NotNull
    @Min(0)
    private int startBudget;
}
