package hana.member.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentLoginResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StudentLoginResDto(Data data) {
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long deptIdx;

        @Builder
        public Data(Long deptIdx) {
            this.deptIdx = deptIdx;
        }
    }
}
