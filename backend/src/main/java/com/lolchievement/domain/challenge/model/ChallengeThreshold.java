package com.lolchievement.domain.challenge.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class ChallengeThreshold {
    private String title;
    private double value;
}
