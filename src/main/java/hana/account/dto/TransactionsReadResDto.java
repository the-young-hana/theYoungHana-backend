package hana.account.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public TransactionsReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String deptName;
        private final String deptAccountNumber;
        private final Long deptAccountBalance;
        private final List<DeptAccountTransaction> deptAccountTransactions;

        @Builder
        public Data(
                String deptName,
                String deptAccountNumber,
                Long deptAccountBalance,
                List<DeptAccountTransaction> deptAccountTransactions) {
            super();
            this.deptName = deptName;
            this.deptAccountNumber = deptAccountNumber;
            this.deptAccountBalance = deptAccountBalance;
            this.deptAccountTransactions = deptAccountTransactions;
        }

        @Getter
        public static class DeptAccountTransaction {
            private final Long transactionIdx;
            private final String transactionId;
            private final String transactionName;
            private final Long transactionAmount;
            private final Long transactionBalance;
            private final String transactionType;
            private final Boolean transactionIsUsed;
            private final LocalDateTime transactionDate;

            @Builder
            public DeptAccountTransaction(
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
}
