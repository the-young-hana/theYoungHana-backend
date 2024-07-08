package hana.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsRemitCreateReqDto {

    private final Long myAccountIdx;
    private final Long amount;
    private final String receiveAccount;

    @Builder
    public TransactionsRemitCreateReqDto(Long myAccountIdx, Long amount, String receiveAccount) {
        this.myAccountIdx = myAccountIdx;
        this.amount = amount;
        this.receiveAccount = receiveAccount;
    }
}
