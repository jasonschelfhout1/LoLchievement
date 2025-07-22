package com.lolchievement.clients.player;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class ExternalPlayerClientPreferencesDTO {
    @JsonProperty
    private String bannerAccent;
    @JsonProperty
    private String title;
    @JsonProperty
    private List<String> challengeIds;
    @JsonProperty
    private String crestBorder;
    @JsonProperty
    private int prestigeCrestBorderLevel;
}
