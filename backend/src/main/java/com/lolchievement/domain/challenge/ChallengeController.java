package com.lolchievement.domain.challenge;

import com.lolchievement.api.ChallengeApi;
import com.lolchievement.clients.riot.challenge.ChallengeClientException;
import com.lolchievement.domain.challenge.model.Tier;
import com.lolchievement.domain.exception.ChallengeNotFoundException;
import com.lolchievement.dto.ChallengeDTO;
import com.lolchievement.dto.PlayerChallengeDTO;
import com.lolchievement.dto.TierDTO;
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

    private final ChallengeFacade challengeFacade;
    private final ChallengeService challengeService;

    @Override
    @GetMapping("/api/challenges/{language}")
    public ResponseEntity<List<ChallengeDTO>> getAllChallenges(@PathVariable String language) {
        log.info("Received request to get all challenges for language: {}", language);
        try {
            List<ChallengeDTO> challenges = challengeFacade.getAllChallenges(language);
            log.info("Successfully retrieved {} challenges", challenges.size());
            return ResponseEntity.ok(challenges);
        } catch (ChallengeClientException e) {
            return handleChallengeClientException(e, "retrieving all challenges");
        } catch (Exception e) {
            log.error("Unexpected error while retrieving all challenges", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping("/api/challenges/{challengeId}/{language}")
    public ResponseEntity<ChallengeDTO> getChallengeDetailByChallengeId(
            @PathVariable String challengeId,
            @PathVariable String language) {
        log.info("Received request for challenge {} in language {}", challengeId, language);
        try {
            Long parsedChallengeId = Long.parseLong(challengeId);
            ChallengeDTO challenge = challengeFacade.getChallengeDetail(parsedChallengeId, language);
            return ResponseEntity.ok(challenge);
        } catch (NumberFormatException e) {
            log.warn("Invalid challenge ID format: {}", challengeId);
            return ResponseEntity.badRequest().build();
        } catch (ChallengeNotFoundException e) {
            log.warn("Challenge not found: {}", challengeId);
            return ResponseEntity.notFound().build();
        } catch (ChallengeClientException e) {
            return handleChallengeClientException(e, "retrieving challenge " + challengeId);
        } catch (Exception e) {
            log.error("Unexpected error retrieving challenge {}", challengeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping("/api/challenges/player/{pUUID}/{language}")
    public ResponseEntity<List<PlayerChallengeDTO>> getPlayerChallengeByPUUID(
            @PathVariable String pUUID,
            @PathVariable String language) {
        log.info("Received request for player {} challenges in language {}", pUUID, language);
        try {
            List<PlayerChallengeDTO> challenges = challengeFacade.getPlayerChallenges(pUUID, language);
            log.info("Successfully retrieved {} challenges for player {}", challenges.size(), pUUID);
            return ResponseEntity.ok(challenges);
        } catch (ChallengeClientException e) {
            return handleChallengeClientException(e, "retrieving challenges for player " + pUUID);
        } catch (Exception e) {
            log.error("Unexpected error retrieving challenges for player {}", pUUID, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    @GetMapping(value = "/api/challenges/token/{challengeId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<Resource> getChallengeToken(
            @PathVariable Long challengeId,
            @RequestParam(defaultValue = "CHALLENGER") TierDTO tier) {
        try {
            Tier cTier = Tier.fromValue(tier);
            if (cTier == Tier.NONE) {
                cTier = Tier.PLATINUM;
            }
            byte[] imageBytes = challengeService.getChallengeTokenByIdAndTier(challengeId, cTier);
            ByteArrayResource resource = new ByteArrayResource(imageBytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (Exception e) {
            log.error("Error retrieving challenge token for challenge {} tier {}", challengeId, tier, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private <T> ResponseEntity<T> handleChallengeClientException(
            ChallengeClientException e, String operation) {
        if (e.getCause() instanceof TooManyRequests) {
            log.warn("Rate limit exceeded while {}", operation);
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        } else {
            log.error("Client error while {}: {}", operation, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
