package com.lolchievement.domain.achievement;

import com.lolchievement.clients.achievement.AchievementClient;
import com.lolchievement.domain.achievement.model.Achievement;
import com.lolchievement.domain.achievement.model.PlayerAchievement;
import com.lolchievement.mapper.AchievementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementClient achievementClient;

    public List<PlayerAchievement> getPlayerAchievements(String pUUID) {
        return AchievementMapper.fromExternalAchievementPlayerDTO(achievementClient.getExternalPlayerAchievements(pUUID));
    }

    public Achievement getAchievementDetail(Long challengeId) {
        return AchievementMapper.fromExternalAchievementDTO(achievementClient.getExternalAchievementConfig(challengeId));
    }
}
