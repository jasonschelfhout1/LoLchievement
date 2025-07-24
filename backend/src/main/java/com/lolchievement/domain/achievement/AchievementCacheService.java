package com.lolchievement.domain.achievement;

import com.lolchievement.clients.cdragon.token.challenge.ChallengeTokenClient;
import com.lolchievement.clients.riot.achievement.AchievementClient;
import com.lolchievement.clients.riot.achievement.ExternalPlayerAchievementDTO;
import com.lolchievement.domain.achievement.model.Achievement;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.AchievementMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AchievementCacheService {
    private final AchievementClient achievementClient;
    private final ChallengeTokenClient tokenChallengeClient;

    @Cacheable(value = "achievementCache", key = "'allAchievements'")
    public Map<Long, Achievement> getAllAchievements() {
        return achievementClient.getExternalAchievementConfigs().stream()
                .map(AchievementMapper::fromExternalAchievementDTO)
                .collect(Collectors.toMap(
                        Achievement::getId,
                        achievement -> achievement
                ));
    }

    @Cacheable(value = "playerAchievementCache", key = "#pUUID")
    public ExternalPlayerAchievementDTO getPlayerAchievements(String pUUID) {
        return achievementClient.getExternalPlayerAchievements(pUUID);
    }

    @Cacheable(value = "tokenChallengeCache", key = "#root.args")
    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return tokenChallengeClient.getChallengeTokenByIdAndTier(challengeId, tier);
    }
}