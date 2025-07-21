package com.lolchievement.domain.service;

import com.lolchievement.clients.PlayerClient;
import com.lolchievement.domain.model.Player;
import com.lolchievement.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerClient playerClient;

    public Player getPlayer(String gameName, String tagLine) {
        return PlayerMapper.fromExternal(playerClient.getExternalPlayer(gameName, tagLine));
    }

    public Player getPlayer(String pUUID) {
        return PlayerMapper.fromExternal(playerClient.getExternalPlayer(pUUID));
    }
}
