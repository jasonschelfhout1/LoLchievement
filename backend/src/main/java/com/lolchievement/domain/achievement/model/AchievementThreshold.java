package com.lolchievement.domain.achievement.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class AchievementThreshold {
    private String title;
    private double value;
}
