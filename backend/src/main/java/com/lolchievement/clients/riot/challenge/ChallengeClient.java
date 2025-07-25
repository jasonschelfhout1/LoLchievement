package com.lolchievement.clients.riot.challenge;

import java.util.List;

public interface ChallengeClient {
    ExternalPlayerChallengeDTO getExternalPlayerChallenges(String pUUID);
    ExternalChallengeConfigDTO getExternalChallengeConfig(Long challengeId);

    List<ExternalChallengeConfigDTO> getExternalChallengeConfigs();
}
