package com.lolchievement.mapper;

import com.lolchievement.domain.controller.dtos.external.ExternalAchievementDTO;
import com.lolchievement.domain.model.Achievement;
import com.lolchievement.dto.AchievementDTO;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class AchievementMapper {

    public static Achievement fromDTO(AchievementDTO dto) {
        return Achievement.builder()
                .challengeId(dto.getChallengeId())
                .level(dto.getLevel())
                .value(dto.getValue())
                .percentile(dto.getPercentile())
                .achievedTime(dto.getAchievedTime())
                .build();
    }

    public static AchievementDTO toDTO(Achievement achievement) {
        return AchievementDTO.builder()
                .challengeId(achievement.getChallengeId())
                .level(achievement.getLevel())
                .value(achievement.getValue())
                .percentile(achievement.getPercentile())
                .achievedTime(achievement.getAchievedTime())
                .build();
    }

    public static List<AchievementDTO> toDTOList(List<Achievement> achievements) {
        return achievements.stream()
                .map(AchievementMapper::toDTO)
                .toList();
    }

    public static List<Achievement> fromDTOList(List<AchievementDTO> achievementDTOS) {
        return achievementDTOS.stream()
                .map(AchievementMapper::fromDTO)
                .toList();
    }

    public static List<Achievement> fromExternalAchievementDTO(ExternalAchievementDTO external) {
        if (external == null || external.getChallenges() == null) {
            return Collections.emptyList();
        }

        return external.getChallenges().stream()
                .map(challenge -> Achievement.builder()
                        .challengeId(challenge.getChallengeId())
                        .level(challenge.getLevel())
                        .value(challenge.getValue())
                        .percentile(challenge.getPercentile())
                        .achievedTime(challenge.getAchievedTime())
                        .build())
                .toList();
    }

}