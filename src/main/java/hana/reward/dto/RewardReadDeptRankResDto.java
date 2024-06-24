package hana.reward.dto;

import hana.common.dto.BaseResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RewardReadDeptRankResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public RewardReadDeptRankResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long rankIdx;
        private final Long deptIdx;
        private final String deptName;
        private final Long deptReward;

        @Builder
        public Data(Long rankIdx, Long deptIdx, String deptName, Long deptReward) {
            this.rankIdx = rankIdx;
            this.deptIdx = deptIdx;
            this.deptName = deptName;
            this.deptReward = deptReward;
        }
    }
}
