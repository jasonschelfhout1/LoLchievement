package com.lolchievement.clients.riot.challenge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolchievement.domain.challenge.model.ChallengeTracking;
import com.lolchievement.dto.State;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@Jacksonized
public class ExternalChallengeConfigDTO {

    @JsonProperty("id")
    private long id;

    @JsonProperty("localizedNames")
    private Map<String, Map<String, String>> localizedNames;

    @JsonProperty("state")
    private State state;

    @JsonProperty("tracking")
    private ChallengeTracking tracking;

    @JsonProperty("startTimestamp")
    private long startTimestamp;

    @JsonProperty("endTimestamp")
    private long endTimestamp;

    @JsonProperty("leaderboard")
    private boolean leaderboard;

    @JsonProperty("thresholds")
    private Map<String, Double> thresholds;
}
