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
        private final DeptAccountInfoDto deptAccountInfo;
        private final List<TransactionsByDateResDto> transactionList;

        @Builder
        public Data(
                DeptAccountInfoDto deptAccountInfo,
                List<TransactionsByDateResDto> deptAccountTransactions) {
            super();
            this.deptAccountInfo = deptAccountInfo;
            this.transactionList = deptAccountTransactions;
        }
    }
}
