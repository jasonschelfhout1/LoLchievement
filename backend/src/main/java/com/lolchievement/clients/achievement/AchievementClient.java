package com.lolchievement.clients.achievement;

import java.util.List;

public interface AchievementClient {
    ExternalPlayerAchievementDTO getExternalPlayerAchievements(String pUUID);
    ExternalChallengeConfigDTO getExternalAchievementConfig(Long challengeId);

    List<ExternalChallengeConfigDTO> getExternalAchievementConfigs();
}
