package hana.account.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsRemitCreateResDto extends BaseResponse {
    private final Data data;

    @Builder
    public TransactionsRemitCreateResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String myAccountName;
        private final Long amount;
        private final String receiverName;

        @Builder
        public Data(String myAccountName, Long amount, String receiverName) {
            this.myAccountName = myAccountName;
            this.amount = amount;
            this.receiverName = receiverName;
        }
    }
}
