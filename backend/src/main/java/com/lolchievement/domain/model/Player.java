package com.lolchievement.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Player {
    private String id;
    private String gameName;
    private String tagLine;
}
