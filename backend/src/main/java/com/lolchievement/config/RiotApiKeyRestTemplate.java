package com.lolchievement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class RiotApiKeyRestTemplate extends RestTemplate {
    @Value("${riot.dev.key}")
    private String apiKey;

    public RiotApiKeyRestTemplate() {
        this.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-Riot-Token", apiKey);
            return execution.execute(request, body);
        });
    }
}