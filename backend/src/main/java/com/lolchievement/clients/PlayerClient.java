package com.lolchievement.clients;

import com.lolchievement.domain.controller.dtos.external.ExternalPlayerDTO;

public interface PlayerClient {
    ExternalPlayerDTO getExternalPlayer(String pUUID);
    ExternalPlayerDTO getExternalPlayer(String gameName, String tagLine);
}
