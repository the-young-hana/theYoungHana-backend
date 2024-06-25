package hana.member.domain;

import lombok.Getter;

@Getter
public enum StudentStatusEnumType {
    재학("재학"),
    휴학("휴학"),
    졸업("졸업"),
    졸업유예("졸업유예"),
    수료("수료");

    private final String description;

    StudentStatusEnumType(String description) {
        this.description = description;
    }
}
