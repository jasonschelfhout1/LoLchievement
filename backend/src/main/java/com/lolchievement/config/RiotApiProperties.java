package com.lolchievement.config;

import com.lolchievement.config.properties.RiotDev;
import com.lolchievement.config.properties.RiotRegionApi;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "riot")
public class RiotApiProperties {
    private RiotDev dev;
    private RiotRegionApi api;
    private String region;
}
