package hana.account.dto;

import hana.common.dto.BaseResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountReadResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public AccountReadResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long accountIdx;
        private final String accountName;
        private final String accountNumber;
        private final Long accountBalance;

        @Builder
        public Data(
                Long accountIdx, String accountName, String accountNumber, Long accountBalance) {
            super();
            this.accountIdx = accountIdx;
            this.accountName = accountName;
            this.accountNumber = accountNumber;
            this.accountBalance = accountBalance;
        }
    }
}
