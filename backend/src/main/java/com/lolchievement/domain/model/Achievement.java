package com.lolchievement.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Achievement {
    private long challengeId;
    private String level;
    private double value;
    private double percentile;
    private long achievedTime;
    private int playersInLevel;
    private int position;
}
