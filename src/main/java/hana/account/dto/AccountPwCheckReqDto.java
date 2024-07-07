package hana.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountPwCheckReqDto {
    private Long accountIdx;
    private String accountPw;

    @Builder
    public AccountPwCheckReqDto(Long accountIdx, String accountPw) {
        this.accountIdx = accountIdx;
        this.accountPw = accountPw;
    }
}
