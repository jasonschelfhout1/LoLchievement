package com.lolchievement.config;

import org.springframework.web.client.RestTemplate;

public class RiotApiKeyRestTemplate extends RestTemplate {
    public RiotApiKeyRestTemplate(RiotApiProperties properties) {
        String apiKey = properties.getDev().getKey();

        this.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("X-Riot-Token", apiKey);
            return execution.execute(request, body);
        });
    }
}