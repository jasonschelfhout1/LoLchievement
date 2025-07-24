package com.lolchievement.clients.cdragon.token.challenge;

import com.lolchievement.config.RiotApiKeyRestTemplate;
import com.lolchievement.dto.Tier;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ChallengeTokenClientImpl implements ChallengeTokenClient {
    private final RestTemplate restTemplate;

    @Value("${cDragon.api.base}")
    private String apiBase;
    @Value("${cDragon.api.assets.base.name}")
    private String assetUri;
    @Value("${cDragon.api.assets.challenges.name}")
    private String challengesUri;

    public ChallengeTokenClientImpl(RiotApiKeyRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        String url = String.format("https://%s%s%s{challengeId}/tokens/{tier}.png",
                apiBase,
                assetUri,
                challengesUri);

        try {
            return restTemplate.getForObject(url, byte[].class, challengeId, tier.getValue().toLowerCase());
        } catch (Exception e) {
            log.error("Failed to fetch image token by challengeId: {} tier: {}", challengeId, tier, e);
            throw new ChallengeTokenClientException("Could not retrieve image token", e);
        }
    }
}