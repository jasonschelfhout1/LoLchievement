package com.lolchievement.clients.riot.challenge;

import com.lolchievement.config.RiotApiKeyRestTemplate;
import com.lolchievement.domain.challenge.model.Tier;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
@Log4j2
public class ChallengeClientImpl implements ChallengeClient {
    private final RiotApiKeyRestTemplate restTemplate;
    private final String baseUrl;

    public ChallengeClientImpl(RiotApiKeyRestTemplate restTemplate,
                               @Value("${riot.region.euw}") String region,
                               @Value("${riot.api.base}") String apiBase,
                               @Value("${riot.api.key.lol.challenges.name}") String challengesUri) {
        this.restTemplate = restTemplate;
        this.baseUrl = String.format("https://%s.%s%s", region, apiBase, challengesUri);
    }

    @Override
    public ExternalPlayerChallengeDTO getExternalPlayerChallenges(String pUUID) {
        String url = baseUrl + "/player-data/{pUuid}";

        try {
            ExternalPlayerChallengeDTO result = restTemplate.getForObject(url, ExternalPlayerChallengeDTO.class, pUUID);
            if (result == null) {
                throw new ChallengeClientException("No challenge data found for player: " + pUUID, null);
            }
            return result;
        } catch (HttpClientErrorException e) {
            handleHttpError(e, "fetch player challenges for pUUID: " + pUUID);
            throw new ChallengeClientException("Could not retrieve player challenges", e);
        } catch (Exception e) {
            log.error("Unexpected error fetching challenges for pUUID: {}", pUUID, e);
            throw new ChallengeClientException("Could not retrieve player challenges", e);
        }
    }

    @Override
    public ExternalChallengeConfigDTO getExternalChallengeConfig(Long challengeId) {
        String url = baseUrl + "/challenges/{challengeId}/config";

        try {
            ExternalChallengeConfigDTO result = restTemplate.getForObject(url, ExternalChallengeConfigDTO.class, challengeId);
            if (result == null) {
                throw new ChallengeClientException("Challenge not found: " + challengeId, null);
            }
            return result;
        } catch (HttpClientErrorException e) {
            handleHttpError(e, "fetch challenge config for ID: " + challengeId);
            throw new ChallengeClientException("Could not retrieve challenge config", e);
        } catch (Exception e) {
            log.error("Unexpected error fetching challenge config for ID: {}", challengeId, e);
            throw new ChallengeClientException("Could not retrieve challenge config", e);
        }
    }

    @Override
    public List<ExternalChallengeConfigDTO> getExternalChallengeConfigs() {
        String url = baseUrl + "/challenges/config";

        try {
            ExternalChallengeConfigDTO[] configs = restTemplate.getForObject(url, ExternalChallengeConfigDTO[].class);
            return configs != null ? Arrays.asList(configs) : Collections.emptyList();
        } catch (HttpClientErrorException e) {
            handleHttpError(e, "fetch all challenge configs");
            throw new ChallengeClientException("Could not retrieve challenge configs", e);
        } catch (Exception e) {
            log.error("Unexpected error fetching all challenge configs", e);
            throw new ChallengeClientException("Could not retrieve challenge configs", e);
        }
    }

    @Override
    public Map<String, List<ExternalChallengeThresholdDTO>> getExternalChallengePercentiles() {
        String url = baseUrl + "/challenges/percentiles";

        try {
            ResponseEntity<Map<String, List<ExternalChallengeThresholdDTO>>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return Objects.requireNonNullElse(response.getBody(), Collections.emptyMap());
        } catch (HttpClientErrorException e) {
            handleHttpError(e, "fetch challenge percentiles");
            throw new ChallengeClientException("Could not retrieve challenge percentiles", e);
        } catch (Exception e) {
            log.error("Unexpected error fetching challenge percentiles", e);
            throw new ChallengeClientException("Could not retrieve challenge percentiles", e);
        }
    }

    @Override
    public Map<Tier, Double> getExternalChallengePercentiles(Long challengeId) {
        String url = baseUrl + "/challenges/{challengeId}/percentiles";

        try {
            ResponseEntity<Map<Tier, Double>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    }, challengeId
            );
            return Objects.requireNonNullElse(response.getBody(), Collections.emptyMap());
        } catch (HttpClientErrorException e) {
            handleHttpError(e, "fetch percentiles for challenge: " + challengeId);
            throw new ChallengeClientException("Could not retrieve challenge percentiles", e);
        } catch (Exception e) {
            log.error("Unexpected error fetching percentiles for challenge: {}", challengeId, e);
            throw new ChallengeClientException("Could not retrieve challenge percentiles", e);
        }
    }

    private void handleHttpError(HttpClientErrorException e, String operation) {
        switch (e.getStatusCode().value()) {
            case 404:
                log.warn("Resource not found while trying to {}: {}", operation, e.getMessage());
                break;
            case 429:
                log.warn("Rate limit exceeded while trying to {}", operation);
                break;
            case 403:
                log.error("Forbidden access while trying to {}: Check API key", operation);
                break;
            default:
                log.error("HTTP error {} while trying to {}: {}", e.getStatusCode(), operation, e.getMessage());
        }
    }
}