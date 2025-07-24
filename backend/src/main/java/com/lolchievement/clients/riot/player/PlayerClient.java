package com.lolchievement.clients.riot.player;

public interface PlayerClient {
    ExternalPlayerDTO getExternalPlayer(String pUUID);
    ExternalPlayerDTO getExternalPlayer(String gameName, String tagLine);
}
