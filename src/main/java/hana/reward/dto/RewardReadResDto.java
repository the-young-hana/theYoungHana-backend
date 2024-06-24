package hana.reward.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RewardReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public RewardReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long myPoint;
        private final Long deptPoint;

        @Builder
        public Data(Long myPoint, Long deptPoint) {
            this.myPoint = myPoint;
            this.deptPoint = deptPoint;
        }
    }
}
