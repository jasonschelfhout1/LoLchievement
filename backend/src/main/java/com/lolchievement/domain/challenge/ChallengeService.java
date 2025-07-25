package com.lolchievement.domain.challenge;

import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.domain.challenge.model.PlayerChallenge;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.ChallengeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class ChallengeService {
    private final ChallengeCacheService cacheService;

    public Challenge getChallengeDetail(Long challengeId) {
        return cacheService.getAllChallenges().get(challengeId);
    }

    public List<Challenge> getChallengesForIds(List<Long> challengeIds) {
        Map<Long, Challenge> challengesMap = cacheService.getAllChallenges();
        return challengeIds.stream()
                .map(challengesMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<PlayerChallenge> getPlayerChallenges(String pUUID) {
        return ChallengeMapper.fromExternalChallengePlayerDTO(
                cacheService.getPlayerChallenges(pUUID)
        );
    }

    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return cacheService.getChallengeTokenByIdAndTier(challengeId, tier);
    }
}