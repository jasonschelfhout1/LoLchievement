package com.lolchievement.clients;

import com.lolchievement.clients.exceptions.AchievementClientException;
import com.lolchievement.config.RiotApiKeyRestTemplate;
import com.lolchievement.domain.controller.dtos.external.ExternalAchievementDTO;
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
    public ExternalAchievementDTO getExternalPlayerAchievements(String pUUID) {
        String url = String.format("https://%s.%s%s/player-data/{pUuid}",
                region,
                apiBase,
                challengesUri);

        try {
            return restTemplate.getForObject(url, ExternalAchievementDTO.class, pUUID);
        } catch (Exception e) {
            log.error("Failed to fetch achievement by pUUID: {}", pUUID, e);
            throw new AchievementClientException("Could not retrieve achievement info", e);
        }
    }
}
