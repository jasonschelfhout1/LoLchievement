package com.lolchievement.domain.service;

import com.lolchievement.clients.AchievementClient;
import com.lolchievement.domain.model.Achievement;
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

    public List<Achievement> getPlayerAchievements(String pUUID) {
        return AchievementMapper.fromExternalAchievementDTO(achievementClient.getExternalPlayerAchievements(pUUID));
    }
}
