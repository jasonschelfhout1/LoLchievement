package com.lolchievement.clients.riot.challenge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolchievement.domain.challenge.model.Tier;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ExternalChallengeThresholdDTO {
    @JsonProperty
    private Tier tier;
    @JsonProperty
    private double value;
}
