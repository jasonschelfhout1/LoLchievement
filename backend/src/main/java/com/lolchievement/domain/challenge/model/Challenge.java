package com.lolchievement.domain.challenge.model;

import com.lolchievement.domain.common.model.Language;
import com.lolchievement.dto.State;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class Challenge {
    private Long id;
    private List<Language> languages;
    private State state;
    private ChallengeTracking tracking;
    private boolean leaderboard;
    private List<ChallengeThreshold> thresholds;
}
