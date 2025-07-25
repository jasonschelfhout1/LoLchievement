package com.lolchievement.domain.challenge.model;

import com.lolchievement.dto.TierDTO;

public enum Tier {
    IRON, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, GRANDMASTER, CHALLENGER, NONE;

    public static Tier fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Tier value cannot be null or empty");
        }

        try {
            return Tier.valueOf(value.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown tier: " + value, e);
        }
    }

    public static Tier fromValue(TierDTO tier) {
        if (tier == null) {
            throw new IllegalArgumentException("TierDTO cannot be null or empty");
        }

        try {
            return Tier.valueOf(tier.getValue().toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown tier: " + tier.getValue(), e);
        }
    }

    public TierDTO toDTO() {
        try {
            return TierDTO.fromValue(this.name());
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to convert to DTO: " + this.name(), e);
        }
    }


}
