package com.lolchievement.domain.achievement.model;

import com.lolchievement.domain.common.model.Language;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class Achievement {
    private Long id;
    private List<Language> languages;
    private AchievementState state;
    private AchievementTracking tracking;
    private boolean leaderboard;
    private List<AchievementThreshold> thresholds;
}
