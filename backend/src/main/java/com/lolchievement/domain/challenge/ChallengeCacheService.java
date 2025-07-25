package com.lolchievement.domain.challenge;

import com.lolchievement.clients.cdragon.token.challenge.ChallengeTokenClient;
import com.lolchievement.clients.riot.challenge.ChallengeClient;
import com.lolchievement.clients.riot.challenge.ExternalChallengeConfigDTO;
import com.lolchievement.clients.riot.challenge.ExternalChallengeThresholdDTO;
import com.lolchievement.clients.riot.challenge.ExternalPlayerChallengeDTO;
import com.lolchievement.domain.challenge.model.Tier;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ChallengeCacheService {
    private final ChallengeClient challengeClient;
    private final ChallengeTokenClient tokenChallengeClient;

    @Cacheable(value = "challengeCache", key = "'allChallenges'")
    public List<ExternalChallengeConfigDTO> getAllChallenges() {
        return challengeClient.getExternalChallengeConfigs();
    }

    @Cacheable(value = "playerChallengeCache", key = "#pUUID")
    public ExternalPlayerChallengeDTO getPlayerChallenges(String pUUID) {
        return challengeClient.getExternalPlayerChallenges(pUUID);
    }

    @Cacheable(value = "tokenChallengeCache", key = "#challengeId + '_' + #tier")
    public byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier) {
        return tokenChallengeClient.getChallengeTokenByIdAndTier(challengeId, tier);
    }

    @Cacheable(value = "challengePercentilesCache", key = "'allChallengePercentiles'")
    public Map<String, List<ExternalChallengeThresholdDTO>> getAllChallengePercentiles() {
        return challengeClient.getExternalChallengePercentiles();
    }

    @Cacheable(value = "challengePercentilesCache", key = "#challengeId")
    public Map<Tier, Double> getChallengePercentile(Long challengeId) {
        return challengeClient.getExternalChallengePercentiles(challengeId);
    }
}