package hana.account.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsReadResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public TransactionsReadResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long transactionIdx;
        private final String transactionId;
        private final String transactionName;
        private final Long transactionAmount;
        private final Long transactionBalance;
        private final String transactionType;
        private final Boolean transactionIsUsed;
        private final LocalDateTime transactionDate;

        @Builder
        public Data(
                Long transactionIdx,
                String transactionId,
                String transactionName,
                Long transactionAmount,
                Long transactionBalance,
                String transactionType,
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
}
