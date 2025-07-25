package com.lolchievement.domain.challenge;

import com.lolchievement.clients.cdragon.token.challenge.ChallengeTokenClient;
import com.lolchievement.clients.riot.challenge.ChallengeClient;
import com.lolchievement.clients.riot.challenge.ExternalPlayerChallengeDTO;
import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.dto.Tier;
import com.lolchievement.mapper.ChallengeMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChallengeCacheService {
    private final ChallengeClient challengeClient;
    private final ChallengeTokenClient tokenChallengeClient;

    @Cacheable(value = "challengeCache", key = "'allChallenges'")
    public Map<Long, Challenge> getAllChallenges() {
        return challengeClient.getExternalChallengeConfigs().stream()
                .map(ChallengeMapper::fromExternalChallengeDTO)
                .collect(Collectors.toMap(
                        Challenge::getId,
                        challenge -> challenge
                ));
    }

    @Cacheable(value = "playerChallengeCache", key = "#pUUID")
    public ExternalPlayerChallengeDTO getPlayerChallenges(String pUUID) {
        return challengeClient.getExternalPlayerChallenges(pUUID);
    }

    @Cacheable(value = "tokenChallengeCache", key = "#root.args")
    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return tokenChallengeClient.getChallengeTokenByIdAndTier(challengeId, tier);
    }
}