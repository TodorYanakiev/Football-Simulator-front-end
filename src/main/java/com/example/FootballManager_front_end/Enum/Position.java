package com.example.FootballManager_front_end.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
    GK("GK"),
    LB("LB"),
    LCB("LCB"),
    CB("CB"),
    RCB("RCB"),
    RB("RB"),
    LM("LM"),
    LCM("LCM"),
    CM("CM"),
    RCM("RCM"),
    RM("RM"),
    RF("RF"),
    LCF("LCF"),
    CF("CF"),
    RCF("RCF"),
    LF("LF");

    private String label;
}
