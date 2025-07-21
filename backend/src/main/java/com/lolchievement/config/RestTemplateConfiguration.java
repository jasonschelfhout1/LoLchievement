package com.lolchievement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RiotApiKeyRestTemplate riotApiKeyRestTemplate(RiotApiProperties properties) {
        return new RiotApiKeyRestTemplate(properties);
    }
}
