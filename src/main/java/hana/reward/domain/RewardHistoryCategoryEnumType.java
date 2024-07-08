package hana.reward.domain;

import lombok.Getter;

@Getter
public enum RewardHistoryCategoryEnumType {
    선물("선물"),
    퀴즈("퀴즈");

    private final String description;

    RewardHistoryCategoryEnumType(String description) {
        this.description = description;
    }
}
