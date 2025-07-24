package com.lolchievement.clients.riot.achievement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ExternalChallengePointDTO {
    @JsonProperty
    private String level;
    @JsonProperty
    private long current;
    @JsonProperty
    private long max;
    @JsonProperty
    private long precentile;
}
