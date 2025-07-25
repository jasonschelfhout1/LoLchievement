package com.lolchievement.mapper;

import com.lolchievement.clients.riot.challenge.ExternalChallengeConfigDTO;
import com.lolchievement.clients.riot.challenge.ExternalPlayerChallengeDTO;
import com.lolchievement.domain.challenge.model.Challenge;
import com.lolchievement.domain.challenge.model.ChallengeThreshold;
import com.lolchievement.domain.challenge.model.PlayerChallenge;
import com.lolchievement.domain.common.model.Language;
import com.lolchievement.domain.exception.LanguageException;
import com.lolchievement.dto.*;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class ChallengeMapper {
    private static final String LANGUAGE_NAME = "name";
    private static final String LANGUAGE_DESCRIPTION = "description";
    private static final String LANGUAGE_SHORT_DESCRIPTION = "shortDescription";

    public static List<PlayerChallengeDTO> toDTOList(List<PlayerChallenge> playerChallenges, List<Challenge> challenges) {
        if (playerChallenges == null || challenges == null) {
            return Collections.emptyList();
        }

        return playerChallenges.stream()
                .map(playerChallenge -> {
                    // Match the corresponding Challenge by challengeId
                    Challenge matchingChallenge = challenges.stream()
                            .filter(challenge -> challenge.getId().equals(playerChallenge.getChallengeId()))
                            .findFirst()
                            .orElse(null);

                    // If no matching Challenge is found, return a basic DTO without meta-info
                    if (matchingChallenge == null) {
                        return PlayerChallengeDTO.builder()
                                .challengeId(playerChallenge.getChallengeId())
                                .level(Tier.valueOf(playerChallenge.getLevel()))
                                .value(playerChallenge.getValue())
                                .percentile(playerChallenge.getPercentile())
                                .achievedTime(playerChallenge.getAchievedTime())
                                .build();
                    }

                    return toDTO(playerChallenge, matchingChallenge);
                })
                .toList();
    }

    public static PlayerChallengeDTO toDTO(PlayerChallenge playerChallenge, Challenge challenge) {
        List<ChallengeThresholdDTO> thresholdDTOS = challenge.getThresholds().stream()
                .map(ChallengeMapper::toDTO)
                .toList();
        return PlayerChallengeDTO.builder()
                .challengeId(playerChallenge.getChallengeId())
                .level(Tier.fromValue(playerChallenge.getLevel()))
                .value(playerChallenge.getValue())
                .percentile(playerChallenge.getPercentile())
                .achievedTime(playerChallenge.getAchievedTime())
                .state(challenge.getState())
                .challengeThreshHolds(thresholdDTOS)
                .build();
    }

    public static List<PlayerChallenge> fromExternalChallengePlayerDTO(ExternalPlayerChallengeDTO external) {
        if (external == null || external.getChallenges() == null) {
            return Collections.emptyList();
        }

        return external.getChallenges().stream()
                .map(challenge -> PlayerChallenge.builder()
                        .challengeId(challenge.getChallengeId())
                        .level(challenge.getLevel())
                        .value(challenge.getValue())
                        .percentile(challenge.getPercentile())
                        .achievedTime(challenge.getAchievedTime())
                        .build())
                .toList();
    }

    public static Challenge fromExternalChallengeDTO(ExternalChallengeConfigDTO external) {
        List<ChallengeThreshold> thresholds = convertToThresholds(external.getThresholds());
        List<Language> languages = convertToLanguages(external.getLocalizedNames());

        return Challenge.builder()
                .id(external.getId())
                .languages(languages)
                .state(external.getState())
                .tracking(external.getTracking())
                .leaderboard(external.isLeaderboard())
                .thresholds(thresholds)
                .build();
    }

    public static ChallengeDTO toDTO(Challenge challenge, String languageFormat) {
        Language currentLanguage = getLanguage(challenge.getLanguages(), languageFormat);

        if (currentLanguage == null) {
            throw new LanguageException("Unknown language: " + languageFormat);
        }

        List<ChallengeThresholdDTO> challengeThresholds = challenge.getThresholds().stream()
                .map(ChallengeMapper::toDTO)
                .toList();

        return ChallengeDTO.builder()
                .challengeId(challenge.getId())
                .name(currentLanguage.getName())
                .description(currentLanguage.getDescription())
                .shortDescription(currentLanguage.getShortDescription())
                .state(challenge.getState())
                .challengeThreshHolds(challengeThresholds)
                .build();
    }

    private static List<ChallengeThreshold> convertToThresholds(Map<String, Double> thresholds) {
        if (thresholds == null) return Collections.emptyList();

        return thresholds.entrySet().stream()
                .map(t ->
                        ChallengeThreshold.builder()
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

    private static ChallengeThresholdDTO toDTO(ChallengeThreshold challengeThreshold) {
        return ChallengeThresholdDTO.builder()
                .title(challengeThreshold.getTitle())
                .value(challengeThreshold.getValue())
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