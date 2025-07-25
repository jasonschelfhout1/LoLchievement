package com.lolchievement.mapper;

import com.lolchievement.clients.riot.challenge.ExternalChallengeConfigDTO;
import com.lolchievement.clients.riot.challenge.ExternalChallengeThresholdDTO;
import com.lolchievement.clients.riot.challenge.ExternalPlayerChallengeDTO;
import com.lolchievement.domain.challenge.model.*;
import com.lolchievement.domain.common.model.Language;
import com.lolchievement.domain.exception.LanguageException;
import com.lolchievement.dto.ChallengeDTO;
import com.lolchievement.dto.ChallengeThresholdDTO;
import com.lolchievement.dto.PlayerChallengeDTO;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ChallengeMapper {
    private static final String LANGUAGE_NAME = "name";
    private static final String LANGUAGE_DESCRIPTION = "description";
    private static final String LANGUAGE_SHORT_DESCRIPTION = "shortDescription";

    // PlayerChallenge mapping
    public static List<PlayerChallengeDTO> toPlayerChallengeDTOList(List<PlayerChallenge> playerChallenges, List<Challenge> challenges, String language) {
        if (isEmpty(playerChallenges) || isEmpty(challenges)) {
            return Collections.emptyList();
        }

        Map<Long, Challenge> challengeMap = challenges.stream()
                .collect(Collectors.toMap(Challenge::getId, Function.identity()));

        return playerChallenges.stream()
                .map(playerChallenge -> toDTO(playerChallenge, challengeMap.get(playerChallenge.getChallengeId()), language))
                .toList();
    }

    public static PlayerChallengeDTO toDTO(PlayerChallenge playerChallenge, Challenge challenge, String language) {
        var currentLanguage = challenge.getLanguages().stream()
                .filter(l -> l.getLanguageFormat().equalsIgnoreCase(language))
                .findFirst()
                .orElseThrow(() -> new LanguageException("Failed to parse language"));

        PlayerChallengeDTO.PlayerChallengeDTOBuilder builder = PlayerChallengeDTO.builder()
                .challengeId(playerChallenge.getChallengeId())
                .tierDTO(playerChallenge.getTier().toDTO())
                .value(playerChallenge.getValue())
                .percentile(playerChallenge.getPercentile())
                .shortDescription(currentLanguage.getShortDescription())
                .description(currentLanguage.getDescription())
                .achievedTime(playerChallenge.getAchievedTime());

        builder.state(challenge.getState())
                .challengeThresholds(toThresholdDTOs(challenge.getThresholds()));

        return builder.build();
    }

    // Challenge mapping
    public static List<ChallengeDTO> toChallengeDTOList(List<Challenge> challenges, List<ChallengePercentile> challengePercentiles, String language) {
        if (isEmpty(challenges)) {
            return Collections.emptyList();
        }

        Map<String, ChallengePercentile> percentileMap = Optional.ofNullable(challengePercentiles)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(ChallengePercentile::getId, Function.identity()));

        return challenges.stream()
                .map(challenge -> {
                    ChallengePercentile percentile = percentileMap.get(challenge.getId().toString());
                    return toDTO(challenge, percentile, language);
                })
                .toList();
    }

    public static ChallengeDTO toDTO(Challenge challenge, ChallengePercentile challengePercentile, String languageFormat) {
        Language currentLanguage = getLanguage(challenge.getLanguages(), languageFormat);

        return ChallengeDTO.builder()
                .challengeId(challenge.getId())
                .name(currentLanguage.getName())
                .description(currentLanguage.getDescription())
                .shortDescription(currentLanguage.getShortDescription())
                .state(challenge.getState())
                .challengeThresholds(toThresholdDTOs(challenge.getThresholds()))
                .challengePercentiles(toPercentileDTOs(challengePercentile))
                .build();
    }

    // External to domain mapping
    public static List<PlayerChallenge> fromExternalPlayerChallengeDTO(ExternalPlayerChallengeDTO external) {
        if (external == null || isEmpty(external.getChallenges())) {
            return Collections.emptyList();
        }

        return external.getChallenges().stream()
                .map(challenge -> PlayerChallenge.builder()
                        .challengeId(challenge.getChallengeId())
                        .tier(Tier.fromValue(challenge.getLevel()))
                        .value(challenge.getValue())
                        .percentile(challenge.getPercentile())
                        .achievedTime(challenge.getAchievedTime())
                        .playersInLevel(challenge.getPlayersInLevel())
                        .position(challenge.getPosition())
                        .build())
                .toList();
    }

    public static Challenge fromExternalChallengeConfigDTO(ExternalChallengeConfigDTO external) {
        return Challenge.builder()
                .id(external.getId())
                .languages(convertToLanguages(external.getLocalizedNames()))
                .state(external.getState())
                .tracking(external.getTracking())
                .leaderboard(external.isLeaderboard())
                .thresholds(convertToThresholds(external.getThresholds()))
                .build();
    }

    public static ChallengePercentile fromExternalChallengePercentile(Long challengeId, Map<Tier, Double> externals) {
        return ChallengePercentile.builder()
                .id(challengeId.toString())
                .percentiles(Objects.requireNonNullElse(externals, Collections.emptyMap()))
                .build();
    }

    public static ChallengePercentile fromExternalCategoryPointDTO(String id, List<ExternalChallengeThresholdDTO> externals) {
        Map<com.lolchievement.domain.challenge.model.Tier, Double> percentiles = Optional.ofNullable(externals)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        dto -> Tier.fromValue(dto.getTier().name()),
                        ExternalChallengeThresholdDTO::getValue
                ));

        return ChallengePercentile.builder()
                .id(id)
                .percentiles(percentiles)
                .build();
    }


    // Private helper methods
    private static List<ChallengeThreshold> convertToThresholds(Map<String, Double> thresholds) {
        if (isEmpty(thresholds)) return Collections.emptyList();

        return thresholds.entrySet().stream()
                .map(entry -> ChallengeThreshold.builder()
                        .tier(Tier.fromValue(entry.getKey()))
                        .value(entry.getValue())
                        .build())
                .toList();
    }

    private static List<Language> convertToLanguages(Map<String, Map<String, String>> localizedNames) {
        if (isEmpty(localizedNames)) return Collections.emptyList();

        return localizedNames.entrySet().stream()
                .map(entry -> {
                    Map<String, String> values = entry.getValue();
                    return Language.builder()
                            .languageFormat(entry.getKey())
                            .name(values.get(LANGUAGE_NAME))
                            .description(values.get(LANGUAGE_DESCRIPTION))
                            .shortDescription(values.get(LANGUAGE_SHORT_DESCRIPTION))
                            .build();
                })
                .toList();
    }

    private static List<ChallengeThresholdDTO> toThresholdDTOs(List<ChallengeThreshold> thresholds) {
        if (isEmpty(thresholds)) return Collections.emptyList();

        return thresholds.stream()
                .map(threshold -> ChallengeThresholdDTO.builder()
                        .tierDTO(threshold.getTier().toDTO())
                        .value(threshold.getValue())
                        .build())
                .toList();
    }

    private static List<ChallengeThresholdDTO> toPercentileDTOs(ChallengePercentile challengePercentile) {
        if (challengePercentile == null || isEmpty(challengePercentile.getPercentiles())) {
            return Collections.emptyList();
        }

        return challengePercentile.getPercentiles().entrySet().stream()
                .map(entry -> ChallengeThresholdDTO.builder()
                        .tierDTO(entry.getKey().toDTO())
                        .value(entry.getValue())
                        .build())
                .toList();
    }

    private static Language getLanguage(List<Language> languages, String languageFormat) {
        if (isEmpty(languages)) {
            throw new LanguageException("No languages available");
        }

        return languages.stream()
                .filter(language -> language.getLanguageFormat().equalsIgnoreCase(languageFormat))
                .findFirst()
                .orElseThrow(() -> new LanguageException("Language not found: " + languageFormat));
    }

    private static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    private static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}