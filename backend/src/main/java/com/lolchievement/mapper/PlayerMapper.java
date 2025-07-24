package com.lolchievement.mapper;

import com.lolchievement.clients.riot.player.ExternalPlayerDTO;
import com.lolchievement.domain.player.model.Player;
import com.lolchievement.dto.PlayerDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerMapper {

    public static Player fromExternal(ExternalPlayerDTO external) {
        return Player.builder()
                .id(external.getPuuid())
                .gameName(external.getGameName())
                .tagLine(external.getTagLine())
                .build();
    }

    public static PlayerDTO toDto(Player player) {
        return PlayerDTO.builder()
                .id(player.getId())
                .gameName(player.getGameName())
                .tagName(player.getTagLine())
                .build();
    }
}
