package com.lolchievement.domain.challenge;

import com.lolchievement.api.ChallengeApi;
import com.lolchievement.clients.riot.challenge.ChallengeClientException;
import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.domain.challenge.model.PlayerChallenge;
import com.lolchievement.dto.ChallengeDTO;
import com.lolchievement.dto.PlayerChallengeDTO;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.ChallengeMapper;
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
public class ChallengeController implements ChallengeApi {

    private final ChallengeService challengeService;

    @Override
    @GetMapping("/api/challenges/{challengeId}/{language}")
    public ResponseEntity<ChallengeDTO> getChallengeDetailByChallengeId(@PathVariable String challengeId, @PathVariable String language) {
        log.info("Received request to get challenge details with challengeId='{}' language='{}'", challengeId, language);
        try {
            Long parsedChallengeId = Long.parseLong(challengeId);
            ChallengeDTO dto = ChallengeMapper.toDTO(challengeService.getChallengeDetail(parsedChallengeId), language);
            log.info("Successfully retrieved challenge details with challengeId='{}'", challengeId);
            return ResponseEntity.ok(dto);
        } catch (ChallengeClientException e) {
            Throwable cause = e.getCause();
            if (cause instanceof TooManyRequests) {
                log.warn("Rate limit exceeded while retrieving challenge detail for challengeId='{}'", challengeId);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            } else {
                log.error("Client error while retrieving challenge detail for challengeId='{}': {}", challengeId, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
            }
        } catch (Exception e) {
            log.error("Unexpected error while retrieving challenge detail for challengeId='{}' language='{}'", challengeId, language, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/api/challenges/token/{challengeId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getChallengeToken(
            @PathVariable Long challengeId,
            @RequestParam(required = false) Tier tier) {
        Tier pTier = Tier.CHALLENGER;
        if (tier != null) {
            pTier = tier;
        }
        byte[] imageBytes = challengeService.getChallengeTokenByIdAndTier(challengeId, pTier);
        ByteArrayResource resource = new ByteArrayResource(imageBytes);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @Override
    @GetMapping("/api/challenges/player/{pUUID}/{language}")
    public ResponseEntity<List<PlayerChallengeDTO>> getPlayerChallengeByPUUID(@PathVariable String pUUID, @PathVariable String language) {
        log.info("Received request to get challenges for player with pUUID='{}' language='{}'", pUUID, language);
        try {
            List<PlayerChallenge> playerChallenges = challengeService.getPlayerChallenges(pUUID);
            List<Long> challengeIds = playerChallenges.stream()
                    .map(PlayerChallenge::getChallengeId)
                    .toList();

            List<Challenge> challenges = challengeService.getChallengesForIds(challengeIds);

            log.info("Successfully retrieved {} challenges for player with pUUID='{}'", challenges.size(), pUUID);
            return ResponseEntity.ok(ChallengeMapper.toDTOList(playerChallenges, challenges));
        } catch (ChallengeClientException e) {
            Throwable cause = e.getCause();
            if (cause instanceof TooManyRequests) {
                log.warn("Rate limit exceeded while retrieving challenges for player pUUID='{}'", pUUID);
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            } else {
                log.error("Client error while retrieving challenges for player pUUID='{}': {}", pUUID, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
            }
        } catch (Exception e) {
            log.error("Unexpected error while retrieving challenges for player with pUUID='{}' language='{}'", pUUID, language, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
