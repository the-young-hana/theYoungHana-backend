package hana.account.dto;

import hana.common.dto.BaseResponse;
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
        private final List<DeptAccountTransactionResDto> deptAccountTransactions;

        @Builder
        public Data(
                String deptName,
                String deptAccountNumber,
                Long deptAccountBalance,
                List<DeptAccountTransactionResDto> deptAccountTransactions) {
            super();
            this.deptName = deptName;
            this.deptAccountNumber = deptAccountNumber;
            this.deptAccountBalance = deptAccountBalance;
            this.deptAccountTransactions = deptAccountTransactions;
        }
    }
}
