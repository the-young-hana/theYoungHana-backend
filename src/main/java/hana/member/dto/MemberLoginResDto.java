package hana.member.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLoginResDto extends BaseResponse {
    private final Data data;

    @Builder
    public MemberLoginResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String accessToken;
        private final String refreshToken;
        private final Long deptIdx;

        @Builder
        public Data(String accessToken, String refreshToken, Long deptIdx) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.deptIdx = deptIdx;
        }
    }
}
