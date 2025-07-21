package com.lolchievement.domain.controller;

import com.lolchievement.api.PlayerApi;
import com.lolchievement.domain.service.PlayerService;
import com.lolchievement.dto.PlayerDTO;
import com.lolchievement.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class PlayerController implements PlayerApi {
    private final PlayerService playerService;

    @Override
    @GetMapping("/api/player/{gameName}/{tagName}")
    public ResponseEntity<PlayerDTO> getPlayerByGameAndTagName(
            @PathVariable String gameName,
            @PathVariable String tagName
    ) {
        log.info("Received request to get player by gameName='{}' and tagName='{}'", gameName, tagName);
        try {
            PlayerDTO dto = PlayerMapper.toDto(playerService.getPlayer(gameName, tagName));
            log.info("Successfully retrieved player by gameName and tagName: {}", dto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Failed to retrieve player by gameName='{}' and tagName='{}'", gameName, tagName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping("/api/player/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable String id) {
        log.info("Received request to get player by ID='{}'", id);
        try {
            PlayerDTO dto = PlayerMapper.toDto(playerService.getPlayer(id));
            log.info("Successfully retrieved player by pUuid: {}", dto);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Failed to retrieve player by ID='{}'", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
