package com.lolchievement.domain.achievement.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PlayerAchievement {
    private long challengeId;
    private String level;
    private double value;
    private double percentile;
    private long achievedTime;
    private int playersInLevel;
    private int position;
}
