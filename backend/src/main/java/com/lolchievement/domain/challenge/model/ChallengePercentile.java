package com.lolchievement.domain.challenge.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@Builder
@EqualsAndHashCode
public class ChallengePercentile {
    private String id;
    private Map<Tier, Double> percentiles;
}
