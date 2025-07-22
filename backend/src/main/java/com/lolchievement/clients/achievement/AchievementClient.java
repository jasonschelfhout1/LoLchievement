package com.lolchievement.clients.achievement;

public interface AchievementClient {
    ExternalPlayerAchievementDTO getExternalPlayerAchievements(String pUUID);

    ExternalChallengeConfigDTO getExternalAchievementConfig(Long challengeId);
}
