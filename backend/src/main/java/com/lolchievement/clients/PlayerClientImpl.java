package com.lolchievement.clients;

import com.lolchievement.clients.exceptions.PlayerClientException;
import com.lolchievement.config.RiotApiKeyRestTemplate;
import com.lolchievement.config.RiotApiProperties;
import com.lolchievement.domain.controller.dtos.external.ExternalPlayerDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class PlayerClientImpl implements PlayerClient {
    private final RiotApiKeyRestTemplate restTemplate;
    private final RiotApiProperties properties;

    @Override
    public ExternalPlayerDTO getExternalPlayer(String pUUID) {
        String url = String.format("https://%s.%s%s/by-puuid/{pUuid}",
                properties.getRegion(),
                properties.getApi().getBase(),
                properties.getApi().getKey().getRiot().getPlayer().getName());

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
                properties.getRegion(),
                properties.getApi().getBase(),
                properties.getApi().getKey().getRiot().getPlayer().getName());

        try {
            return restTemplate.getForObject(url, ExternalPlayerDTO.class, gameName, tagLine);
        } catch (Exception e) {
            log.error("Failed to fetch player by Riot ID: {}#{}", gameName, tagLine, e);
            throw new PlayerClientException("Could not retrieve player info", e);
        }
    }
}
