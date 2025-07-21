package com.lolchievement.clients;

import com.lolchievement.clients.exceptions.PlayerClientException;
import com.lolchievement.config.RiotApiKeyRestTemplate;
import com.lolchievement.domain.controller.dtos.external.ExternalPlayerDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PlayerClientImpl implements PlayerClient {
    private final RiotApiKeyRestTemplate restTemplate;

    @Value("${riot.region.europe}")
    private String region;
    @Value("${riot.api.base}")
    private String apiBase;
    @Value("${riot.api.key.riot.player.name}")
    private String playerUri;

    public PlayerClientImpl(RiotApiKeyRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public ExternalPlayerDTO getExternalPlayer(String pUUID) {
        String url = String.format("https://%s.%s%s/by-puuid/{pUuid}",
                region,
                apiBase,
                playerUri);

        try {
            return restTemplate.getForObject(url, ExternalPlayerDTO.class, pUUID);
        } catch (Exception e) {
            log.error("Failed to fetch player by PUUID: {}", pUUID, e);
            throw new PlayerClientException("Could not retrieve player info", e);
        }
    }

    @Override
    public ExternalPlayerDTO getExternalPlayer(String gameName, String tagLine) {
        String url = String.format("https://%s.%s%s/by-riot-id/{gameName}/{tagLine}",
                region,
                apiBase,
                playerUri);

        try {
            return restTemplate.getForObject(url, ExternalPlayerDTO.class, gameName, tagLine);
        } catch (Exception e) {
            log.error("Failed to fetch player by Riot ID: {}#{}", gameName, tagLine, e);
            throw new PlayerClientException("Could not retrieve player info", e);
        }
    }
}
