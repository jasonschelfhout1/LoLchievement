package com.lolchievement.clients.achievement;

import com.lolchievement.config.RiotApiKeyRestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AchievementClientImpl implements AchievementClient {
    private final RiotApiKeyRestTemplate restTemplate;

    @Value("${riot.region.euw}")
    private String region;
    @Value("${riot.api.base}")
    private String apiBase;
    @Value("${riot.api.key.lol.challenges.name}")
    private String challengesUri;

    public AchievementClientImpl(RiotApiKeyRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ExternalPlayerAchievementDTO getExternalPlayerAchievements(String pUUID) {
        String url = String.format("https://%s.%s%s/player-data/{pUuid}",
                region,
                apiBase,
                challengesUri);

        try {
            return restTemplate.getForObject(url, ExternalPlayerAchievementDTO.class, pUUID);
        } catch (Exception e) {
            log.error("Failed to fetch achievement by pUUID: {}", pUUID, e);
            throw new AchievementClientException("Could not retrieve achievement info", e);
        }
    }

    @Override
    public ExternalChallengeConfigDTO getExternalAchievementConfig(Long challengeId) {
        String url = String.format("https://%s.%s%s/challenges/{challengeId}/config",
                region,
                apiBase,
                challengesUri);

        try {
            return restTemplate.getForObject(url, ExternalChallengeConfigDTO.class, challengeId);
        } catch (Exception e) {
            log.error("Failed to fetch achievement details by challengeId: {}", challengeId, e);
            throw new AchievementClientException("Could not retrieve achievement info detail", e);
        }
    }
}
