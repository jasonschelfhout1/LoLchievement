
package com.lolchievement.domain.achievement;

import com.lolchievement.domain.achievement.model.Achievement;
import com.lolchievement.domain.achievement.model.AchievementThreshold;
import com.lolchievement.domain.achievement.model.PlayerAchievement;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.AchievementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AchievementService {
    private final AchievementCacheService cacheService;

    public Achievement getAchievementDetail(Long challengeId) {
        return cacheService.getAllAchievements().get(challengeId);
    }

    public List<Achievement> getAchievementsForIds(List<Long> challengeIds) {
        Map<Long, Achievement> achievementsMap = cacheService.getAllAchievements();
        return challengeIds.stream()
                .map(achievementsMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<PlayerAchievement> getPlayerAchievements(String pUUID) {
        return AchievementMapper.fromExternalAchievementPlayerDTO(
                cacheService.getPlayerAchievements(pUUID)
        );
    }

    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return cacheService.getChallengeTokenByIdAndTier(challengeId, tier);
    }
}