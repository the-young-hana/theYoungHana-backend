package hana.account.dto;

import hana.account.domain.TransactionTypeEnumType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class DeptAccountTransactionResDto {
    private final Long transactionIdx;
    private final String transactionId;
    private final String transactionName;
    private final Long transactionAmount;
    private final Long transactionBalance;
    private final TransactionTypeEnumType transactionType;
    private final Boolean transactionIsUsed;
    private final LocalDateTime transactionDate;

    public DeptAccountTransactionResDto(
            Long transactionIdx,
            String transactionId,
            String transactionName,
            Long transactionAmount,
            Long transactionBalance,
            TransactionTypeEnumType transactionType,
            Boolean transactionIsUsed,
            LocalDateTime transactionDate) {
        this.transactionIdx = transactionIdx;
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionBalance = transactionBalance;
        this.transactionType = transactionType;
        this.transactionIsUsed = transactionIsUsed;
        this.transactionDate = transactionDate;
    }
}
