package hana.account.domain;

import lombok.Getter;

@Getter
public enum TransactionTypeEnumType {
    입금("입금"),
    출금("출금");

    private final String description;

    TransactionTypeEnumType(String description) {
        this.description = description;
    }
}
