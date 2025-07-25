package com.lolchievement.clients.riot.challenge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lolchievement.clients.riot.player.ExternalPlayerClientPreferencesDTO;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Jacksonized
public class ExternalPlayerChallengeDTO {
    @JsonProperty
    private List<ExternalChallengeInfoDTO> challenges;
    @JsonProperty
    private ExternalPlayerClientPreferencesDTO preferences;
    @JsonProperty
    private ExternalChallengePointDTO totalPoints;
    @JsonProperty
    private Map<String, ExternalChallengePointDTO> categoryPoints;
}
