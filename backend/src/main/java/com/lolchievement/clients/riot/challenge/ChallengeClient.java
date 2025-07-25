package com.lolchievement.clients.riot.challenge;


import com.lolchievement.domain.challenge.model.Tier;

import java.util.List;
import java.util.Map;

public interface ChallengeClient {
    ExternalPlayerChallengeDTO getExternalPlayerChallenges(String pUUID);
    ExternalChallengeConfigDTO getExternalChallengeConfig(Long challengeId);

    List<ExternalChallengeConfigDTO> getExternalChallengeConfigs();

    Map<String, List<ExternalChallengeThresholdDTO>> getExternalChallengePercentiles();

    Map<Tier, Double> getExternalChallengePercentiles(Long challengeId);
}
