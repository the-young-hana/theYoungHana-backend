package hana.event.domain;

import lombok.Getter;

@Getter
public enum EventEnumType {
    신청("신청"),
    응모("응모"),
    선착("선착");
    private final String description;

    EventEnumType(String description) {
        this.description = description;
    }
}
