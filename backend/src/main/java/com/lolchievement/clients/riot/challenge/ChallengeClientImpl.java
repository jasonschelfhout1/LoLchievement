package com.lolchievement.clients.riot.challenge;

import com.lolchievement.config.RiotApiKeyRestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class ChallengeClientImpl implements ChallengeClient {
    private final RiotApiKeyRestTemplate restTemplate;

    @Value("${riot.region.euw}")
    private String region;
    @Value("${riot.api.base}")
    private String apiBase;
    @Value("${riot.api.key.lol.challenges.name}")
    private String challengesUri;

    public ChallengeClientImpl(RiotApiKeyRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ExternalPlayerChallengeDTO getExternalPlayerChallenges(String pUUID) {
        String url = String.format("https://%s.%s%s/player-data/{pUuid}",
                region,
                apiBase,
                challengesUri);

        try {
            return restTemplate.getForObject(url, ExternalPlayerChallengeDTO.class, pUUID);
        } catch (Exception e) {
            log.error("Failed to fetch challenge by pUUID: {}", pUUID, e);
            throw new ChallengeClientException("Could not retrieve challenge info", e);
        }
    }

    @Override
    public ExternalChallengeConfigDTO getExternalChallengeConfig(Long challengeId) {
        String url = String.format("https://%s.%s%s/challenges/{challengeId}/config",
                region,
                apiBase,
                challengesUri);

        try {
            return restTemplate.getForObject(url, ExternalChallengeConfigDTO.class, challengeId);
        } catch (Exception e) {
            log.error("Failed to fetch challenge details by challengeId: {}", challengeId, e);
            throw new ChallengeClientException("Could not retrieve challenge info detail", e);
        }
    }

    @Override
    public List<ExternalChallengeConfigDTO> getExternalChallengeConfigs() {
        String url = String.format("https://%s.%s%s/challenges/config",
                region,
                apiBase,
                challengesUri);

        try {
            ExternalChallengeConfigDTO[] configs = restTemplate.getForObject(url, ExternalChallengeConfigDTO[].class);
            return configs != null ? Arrays.asList(configs) : Collections.emptyList();
        } catch (Exception e) {
            log.error("Failed to fetch all challenges", e);
            throw new ChallengeClientException("Could not retrieve all challenges", e);
        }
    }
}