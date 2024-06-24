package hana.event.dto;

import hana.common.dto.BaseResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventReadWinnersResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public EventReadWinnersResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long prizeRank;
        private final String prizeName;
        private final List<Winner> winnerList;

        @Builder
        public Data(Long prizeRank, String prizeName, List<Winner> winnerList) {
            this.prizeRank = prizeRank;
            this.prizeName = prizeName;
            this.winnerList = winnerList;
        }

        @Getter
        public static class Winner {
            private final String memberId;
            private final String memberName;

            @Builder
            public Winner(String memberId, String memberName) {
                this.memberId = memberId;
                this.memberName = memberName;
            }
        }
    }
}
