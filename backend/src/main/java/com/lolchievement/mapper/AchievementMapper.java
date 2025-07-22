package com.lolchievement.mapper;

import com.lolchievement.clients.achievement.ExternalChallengeConfigDTO;
import com.lolchievement.clients.achievement.ExternalPlayerAchievementDTO;
import com.lolchievement.domain.achievement.model.Achievement;
import com.lolchievement.domain.achievement.model.AchievementThreshold;
import com.lolchievement.domain.achievement.model.PlayerAchievement;
import com.lolchievement.domain.common.model.Language;
import com.lolchievement.domain.exception.LanguageException;
import com.lolchievement.dto.AchievementDTO;
import com.lolchievement.dto.AchievementThresholdDTO;
import com.lolchievement.dto.PlayerAchievementDTO;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@UtilityClass
public class AchievementMapper {
    private static final String LANGUAGE_NAME = "name";
    private static final String LANGUAGE_DESCRIPTION = "description";
    private static final String LANGUAGE_SHORT_DESCRIPTION = "shortDescription";

    public static List<PlayerAchievementDTO> toDTOList(List<PlayerAchievement> playerAchievements, List<Achievement> achievements) {
        if (playerAchievements == null || achievements == null) {
            return Collections.emptyList();
        }

        return playerAchievements.stream()
                .map(playerAchievement -> {
                    // Match the corresponding Achievement by challengeId
                    Achievement matchingAchievement = achievements.stream()
                            .filter(achievement -> achievement.getId().equals(playerAchievement.getChallengeId()))
                            .findFirst()
                            .orElse(null);

                    // If no matching Achievement is found, return a basic DTO without meta-info
                    if (matchingAchievement == null) {
                        return PlayerAchievementDTO.builder()
                                .challengeId(playerAchievement.getChallengeId())
                                .level(playerAchievement.getLevel())
                                .value(playerAchievement.getValue())
                                .percentile(playerAchievement.getPercentile())
                                .achievedTime(playerAchievement.getAchievedTime())
                                .build();
                    }

                    return toDTO(playerAchievement, matchingAchievement);
                })
                .toList();
    }

    public static PlayerAchievementDTO toDTO(PlayerAchievement playerAchievement, Achievement achievement) {
        var state = PlayerAchievementDTO.StateEnum.fromValue(achievement.getState().name());
        List<AchievementThresholdDTO> thresholdDTOS = achievement.getThresholds().stream()
                .map(AchievementMapper::toDTO)
                .toList();
        return PlayerAchievementDTO.builder()
                .challengeId(playerAchievement.getChallengeId())
                .level(playerAchievement.getLevel())
                .value(playerAchievement.getValue())
                .percentile(playerAchievement.getPercentile())
                .achievedTime(playerAchievement.getAchievedTime())
                .state(state)
                .achievementThreshHolds(thresholdDTOS)
                .build();
    }

    public static List<PlayerAchievement> fromExternalAchievementPlayerDTO(ExternalPlayerAchievementDTO external) {
        if (external == null || external.getChallenges() == null) {
            return Collections.emptyList();
        }

        return external.getChallenges().stream()
                .map(challenge -> PlayerAchievement.builder()
                        .challengeId(challenge.getChallengeId())
                        .level(challenge.getLevel())
                        .value(challenge.getValue())
                        .percentile(challenge.getPercentile())
                        .achievedTime(challenge.getAchievedTime())
                        .build())
                .toList();
    }

    public static Achievement fromExternalAchievementDTO(ExternalChallengeConfigDTO external) {
        List<AchievementThreshold> thresholds = convertToThresholds(external.getThresholds());
        List<Language> languages = convertToLanguages(external.getLocalizedNames());

        return Achievement.builder()
                .id(external.getId())
                .languages(languages)
                .state(external.getState())
                .tracking(external.getTracking())
                .leaderboard(external.isLeaderboard())
                .thresholds(thresholds)
                .build();
    }

    public static AchievementDTO toDTO(Achievement achievement, String languageFormat) {
        Language currentLanguage = getLanguage(achievement.getLanguages(), languageFormat);

        if (currentLanguage == null) {
            throw new LanguageException("Unknown language: " + languageFormat);
        }

        List<AchievementThresholdDTO> achievementThresholds = achievement.getThresholds().stream()
                .map(AchievementMapper::toDTO)
                .toList();

        return AchievementDTO.builder()
                .challengeId(achievement.getId())
                .name(currentLanguage.getName())
                .description(currentLanguage.getDescription())
                .shortDescription(currentLanguage.getShortDescription())
                .state(AchievementDTO.StateEnum.fromValue(achievement.getState().name()))
                .achievementThreshHolds(achievementThresholds)
                .build();
    }

    private static List<AchievementThreshold> convertToThresholds(Map<String, Double> thresholds) {
        if (thresholds == null) return Collections.emptyList();

        return thresholds.entrySet().stream()
                .map(t ->
                        AchievementThreshold.builder()
                                .title(t.getKey())
                                .value(t.getValue())
                                .build()
                )
                .toList();
    }

    private static List<Language> convertToLanguages(Map<String, Map<String, String>> localizedNames) {
        if (localizedNames == null) return Collections.emptyList();

        return localizedNames.entrySet().stream()
                .map(entry -> {
                    String format = entry.getKey();
                    Map<String, String> values = entry.getValue();

                    return Language.builder()
                            .format(format)
                            .name(values.get(LANGUAGE_NAME))
                            .description(values.get(LANGUAGE_DESCRIPTION))
                            .shortDescription(values.get(LANGUAGE_SHORT_DESCRIPTION))
                            .build();
                })
                .toList();
    }

    private static AchievementThresholdDTO toDTO(AchievementThreshold achievementThreshold) {
        return AchievementThresholdDTO.builder()
                .title(achievementThreshold.getTitle())
                .value(achievementThreshold.getValue())
                .build();
    }

    private static Language getLanguage(List<Language> languages, String languageFormat) throws LanguageException {
        if (languages == null || languages.isEmpty()) return null;

        return languages.stream()
                .filter(language -> language.getFormat().equalsIgnoreCase(languageFormat))
                .findFirst()
                .orElseThrow(() -> new LanguageException(String.format("Unable to parse %s", languageFormat)));
    }

}