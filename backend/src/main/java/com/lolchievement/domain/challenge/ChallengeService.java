package com.lolchievement.domain.challenge;

import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.domain.challenge.model.ChallengePercentile;
import com.lolchievement.domain.challenge.model.PlayerChallenge;
import com.lolchievement.domain.challenge.model.Tier;
import com.lolchievement.mapper.ChallengeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ChallengeService {
    private final ChallengeCacheService cacheService;

    public Challenge getChallengeById(Long challengeId) {
        return cacheService.getAllChallenges().stream()
                .map(ChallengeMapper::fromExternalChallengeConfigDTO)
                .collect(Collectors.toMap(Challenge::getId, Function.identity())).get(challengeId);
    }

    public List<Challenge> getChallengesForIds(List<Long> challengeIds) {
        Map<Long, Challenge> challengeMap = cacheService.getAllChallenges().stream()
                .map(ChallengeMapper::fromExternalChallengeConfigDTO)
                .collect(Collectors.toMap(Challenge::getId, Function.identity()));
        return challengeIds.stream()
                .map(challengeMap::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Challenge> getAllChallenges() {
        return new ArrayList<>(cacheService.getAllChallenges().stream()
                .map(ChallengeMapper::fromExternalChallengeConfigDTO)
                .collect(Collectors.toMap(Challenge::getId, Function.identity())).values());
    }

    public List<PlayerChallenge> getPlayerChallenges(String pUUID) {
        return ChallengeMapper.fromExternalPlayerChallengeDTO(
                cacheService.getPlayerChallenges(pUUID)
        );
    }

    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return cacheService.getChallengeTokenByIdAndTier(challengeId, tier);
    }

    public List<ChallengePercentile> getChallengePercentiles() {
        return cacheService.getAllChallengePercentiles()
                .entrySet()
                .stream()
                .map(entry -> ChallengeMapper.fromExternalCategoryPointDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    public ChallengePercentile getChallengePercentile(Long challengeId) {
        return ChallengeMapper.fromExternalChallengePercentile(
                challengeId,
                cacheService.getChallengePercentile(challengeId)
        );
    }
}