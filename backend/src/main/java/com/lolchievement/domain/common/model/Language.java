package com.lolchievement.domain.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class Language {
    private String languageFormat;
    private String name;
    private String description;
    private String shortDescription;
}
