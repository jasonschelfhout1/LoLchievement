package com.lolchievement.domain.challenge;

import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.domain.challenge.model.ChallengePercentile;
import com.lolchievement.domain.challenge.model.PlayerChallenge;
import com.lolchievement.domain.exception.ChallengeNotFoundException;
import com.lolchievement.dto.ChallengeDTO;
import com.lolchievement.dto.PlayerChallengeDTO;
import com.lolchievement.mapper.ChallengeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChallengeFacade {
    private final ChallengeService challengeService;

    public List<ChallengeDTO> getAllChallenges(String language) {
        List<Challenge> challenges = challengeService.getAllChallenges();
        List<ChallengePercentile> challengePercentiles = challengeService.getChallengePercentiles();
        return ChallengeMapper.toChallengeDTOList(challenges, challengePercentiles, language);
    }

    public ChallengeDTO getChallengeDetail(Long challengeId, String language) {
        Challenge challenge = challengeService.getChallengeById(challengeId);
        if (challenge == null) {
            throw new ChallengeNotFoundException("Challenge not found: " + challengeId);
        }
        ChallengePercentile percentile = challengeService.getChallengePercentile(challengeId);
        return ChallengeMapper.toDTO(challenge, percentile, language);
    }

    public List<PlayerChallengeDTO> getPlayerChallenges(String pUUID, String language) {
        List<PlayerChallenge> playerChallenges = challengeService.getPlayerChallenges(pUUID);
        List<Long> challengeIds = playerChallenges.stream()
                .map(PlayerChallenge::getChallengeId)
                .toList();

        List<Challenge> challenges = challengeService.getChallengesForIds(challengeIds);
        return ChallengeMapper.toPlayerChallengeDTOList(playerChallenges, challenges, language);
    }
}