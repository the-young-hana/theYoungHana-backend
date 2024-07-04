package hana.event.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventJoinResDto extends BaseResponse {
    private final Data data;

    @Builder
    public EventJoinResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long eventLimit;
        private final Long eventCount;

        @Builder
        public Data(Long eventLimit, Long eventCount) {
            this.eventLimit = eventLimit;
            this.eventCount = eventCount;
        }
    }
}
