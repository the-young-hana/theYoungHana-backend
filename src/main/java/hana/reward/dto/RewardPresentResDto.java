package hana.reward.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RewardPresentResDto extends BaseResponse {
    private final Data data;

    @Builder
    public RewardPresentResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long point;

        @Builder
        public Data(Long point) {
            this.point = point;
        }
    }
}
