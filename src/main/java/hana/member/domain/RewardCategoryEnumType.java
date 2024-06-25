package hana.member.domain;

import lombok.Getter;

@Getter
public enum RewardCategoryEnumType {
    선물("선물"),
    퀴즈("퀴즈");

    private final String description;

    RewardCategoryEnumType(String description) {
        this.description = description;
    }
}
