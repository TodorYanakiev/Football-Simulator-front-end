package com.example.FootballManager_front_end.DTO;

import com.example.FootballManager_front_end.Enum.Position;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseFootballPlayerDTO {
    private Long id;
    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @NotEmpty
    @Size(min = 2)
    private String nationality;

    @NotNull
    @Min(15)
    private Byte age;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte shirtNumber;

    @NotNull
    private Position position;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startDefending;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startSpeed;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startDribble;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startScoring;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startPassing;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startStamina;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startPositioning;

    @NotNull
    @Min(1)
    @Max(99)
    private Byte startGoalkeeping;
}
