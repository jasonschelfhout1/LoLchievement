package com.lolchievement.clients.cdragon.token.challenge;

import com.lolchievement.dto.Tier;

public interface ChallengeTokenClient {
    byte[] getChallengeTokenByIdAndTier(Long challengeId, Tier tier);
}
