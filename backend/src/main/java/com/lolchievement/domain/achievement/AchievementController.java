package com.lolchievement.domain.achievement;

import com.lolchievement.api.AchievementApi;
import com.lolchievement.clients.riot.achievement.AchievementClientException;
import com.lolchievement.domain.achievement.model.Achievement;
import com.lolchievement.domain.achievement.model.PlayerAchievement;
import com.lolchievement.dto.AchievementDTO;
import com.lolchievement.dto.PlayerAchievementDTO;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.AchievementMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class AchievementController implements AchievementApi {

    private final AchievementService achievementService;

    @Override
    @GetMapping("/api/achievements/{challengeId}/{language}")
    public ResponseEntity<AchievementDTO> getAchievementDetailByChallengeId(@PathVariable String challengeId, @PathVariable String language) {
        log.info("Received request to get achievement details with challengeId='{}' language='{}'", challengeId, language);
        try {
            Long parsedChallengeId = Long.parseLong(challengeId);
            AchievementDTO dto = AchievementMapper.toDTO(achievementService.getAchievementDetail(parsedChallengeId), language);
            log.info("Successfully retrieved achievement details with challengeId='{}'", challengeId);
            return ResponseEntity.ok(dto);
        } catch (AchievementClientException e) {
            Throwable cause = e.getCause();
            if (cause instanceof TooManyRequests) {
                log.warn("Rate limit exceeded while retrieving achievement detail for challengeId='{}'", challengeId);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            } else {
                log.error("Client error while retrieving achievement detail for challengeId='{}': {}", challengeId, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
            }
        } catch (Exception e) {
            log.error("Unexpected error while retrieving achievement detail for challengeId='{}' language='{}'", challengeId, language, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/api/achievements/token/{challengeId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getChallengeToken(
            @PathVariable Long challengeId,
            @RequestParam(required = false) Tier tier) {
        Tier pTier = Tier.CHALLENGER;
        if (tier != null) {
            pTier = tier;
        }
        byte[] imageBytes = achievementService.getChallengeTokenByIdAndTier(challengeId, pTier);
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @Override
    @GetMapping("/api/achievements/player/{pUUID}/{language}")
    public ResponseEntity<List<PlayerAchievementDTO>> getPlayerAchievementByPUUID(@PathVariable String pUUID, @PathVariable String language) {
        log.info("Received request to get achievements for player with pUUID='{}' language='{}'", pUUID, language);
        try {
            List<PlayerAchievement> playerAchievements = achievementService.getPlayerAchievements(pUUID);
            List<Long> challengeIds = playerAchievements.stream()
                    .map(PlayerAchievement::getChallengeId)
                    .toList();

            List<Achievement> achievements = achievementService.getAchievementsForIds(challengeIds);

            log.info("Successfully retrieved {} achievements for player with pUUID='{}'", achievements.size(), pUUID);
            return ResponseEntity.ok(AchievementMapper.toDTOList(playerAchievements, achievements));
        } catch (AchievementClientException e) {
            Throwable cause = e.getCause();
            if (cause instanceof TooManyRequests) {
                log.warn("Rate limit exceeded while retrieving achievements for player pUUID='{}'", pUUID);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            } else {
                log.error("Client error while retrieving achievements for player pUUID='{}': {}", pUUID, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
            }
        } catch (Exception e) {
            log.error("Unexpected error while retrieving achievements for player with pUUID='{}' language='{}'", pUUID, language, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
