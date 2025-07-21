package com.lolchievement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RiotApiKeyRestTemplate riotApiKeyRestTemplate() {
        return new RiotApiKeyRestTemplate();
    }
}
