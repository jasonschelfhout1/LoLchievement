package com.lolchievement.domain.achievement.model;

import lombok.AllArgsConstructor;
import lombok.Generated;

@AllArgsConstructor
@Generated
public enum AchievementState {
    DISABLED("DISABLED"),

    HIDDEN("HIDDEN"),

    ENABLED("ENABLED"),

    ARCHIVED("ARCHIVED");

    private String value;

    public static AchievementState fromValue(String value) {
        for (AchievementState a : AchievementState.values()) {
            if (a.value.equalsIgnoreCase(value)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
