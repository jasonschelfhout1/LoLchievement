package com.lolchievement.clients;

import com.lolchievement.domain.controller.dtos.external.ExternalAchievementDTO;

public interface AchievementClient {
    ExternalAchievementDTO getExternalPlayerAchievements(String pUUID);
}
