package com.lolchievement.clients.riot.player;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ExternalPlayerDTO {
    @JsonProperty
    private String puuid;
    @JsonProperty
    private String gameName;
    @JsonProperty
    private String tagLine;
}
