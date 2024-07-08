package hana.account.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountPwCheckResDto extends BaseResponse {
    private final Data data;

    @Builder
    public AccountPwCheckResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Boolean isPwCorrect;

        @Builder
        public Data(Boolean isPwCorrect) {
            super();
            this.isPwCorrect = isPwCorrect;
        }
    }
}
