package com.lolchievement.clients.riot.achievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ExternalChallengeInfoDTO {
    @JsonProperty
    private double percentile;
    @JsonProperty
    private int playersInLevel;
    @JsonProperty
    private long achievedTime;
    @JsonProperty
    private double value;
    @JsonProperty
    private long challengeId;
    @JsonProperty
    private String level;
    @JsonProperty
    private int position;
}
