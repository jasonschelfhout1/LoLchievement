package com.lolchievement.domain.controller;

import com.lolchievement.api.AchievementApi;
import com.lolchievement.domain.service.AchievementService;
import com.lolchievement.dto.AchievementDTO;
import com.lolchievement.mapper.AchievementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class AchievementController implements AchievementApi {

    private final AchievementService achievementService;

    @Override
    @GetMapping("/api/achievements/player/{pUUID}")
    public ResponseEntity<List<AchievementDTO>> getPlayerAchievementByPUUID(@PathVariable String pUUID) {
        log.info("Received request to get achievements for player with pUUID='{}'", pUUID);
        try {
            List<AchievementDTO> achievements = AchievementMapper.toDTOList(
                    achievementService.getPlayerAchievements(pUUID));
            log.info("Successfully retrieved {} achievements for player with pUUID='{}'", achievements.size(), pUUID);
            return ResponseEntity.ok(achievements);
        } catch (Exception e) {
            log.error("Failed to retrieve achievements for player with pUUID='{}'", pUUID, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
