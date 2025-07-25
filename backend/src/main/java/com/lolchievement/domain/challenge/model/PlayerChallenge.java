package com.lolchievement.domain.challenge.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PlayerChallenge {
    private Long challengeId;
    private Tier tier;
    private double value;
    private double percentile;
    private long achievedTime;
    private int playersInLevel;
    private int position;
}
