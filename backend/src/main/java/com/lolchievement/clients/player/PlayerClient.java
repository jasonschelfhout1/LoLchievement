package com.lolchievement.clients.player;

public interface PlayerClient {
    ExternalPlayerDTO getExternalPlayer(String pUUID);
    ExternalPlayerDTO getExternalPlayer(String gameName, String tagLine);
}
