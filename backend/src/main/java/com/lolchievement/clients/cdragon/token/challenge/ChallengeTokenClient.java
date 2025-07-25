package com.lolchievement.clients.cdragon.token.challenge;


import com.lolchievement.domain.challenge.model.Tier;

public interface ChallengeTokenClient {
    byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier);
}
