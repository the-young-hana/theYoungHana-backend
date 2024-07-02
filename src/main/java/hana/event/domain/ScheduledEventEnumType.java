package hana.event.domain;

import lombok.Getter;

@Getter
public enum ScheduledEventEnumType {
    신청_시작("신청_시작"),
    신청_마감("신청_마감"),
    입금_시작("입금_시작"),
    입금_마감("입금_마감"),
    응모_시작("응모_시작"),
    선착_시작("선착_시작"),
    선착_마감("선착_마감");

    private final String description;

    ScheduledEventEnumType(String description) {
        this.description = description;
    }
}
