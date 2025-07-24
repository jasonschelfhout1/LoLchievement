package com.lolchievement.clients.riot.achievement;

import java.util.List;

public interface AchievementClient {
    ExternalPlayerAchievementDTO getExternalPlayerAchievements(String pUUID);
    ExternalChallengeConfigDTO getExternalAchievementConfig(Long challengeId);

    List<ExternalChallengeConfigDTO> getExternalAchievementConfigs();
}
