package hana.account.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsRemitCreateReqDto {

    private final Long myAccountIdx;
    private final Long amount;
    private final String receiveAccount;
    private final Long deptIdx;

    @Builder
    public TransactionsRemitCreateReqDto(
            Long myAccountIdx, Long amount, String receiveAccount, Long deptIdx) {
        this.myAccountIdx = myAccountIdx;
        this.amount = amount;
        this.deptIdx = deptIdx;
        this.receiveAccount = receiveAccount;
    }
}
