package hana.event.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventsReadResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public EventsReadResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long eventIdx;
        private final String eventTitle;
        private final String eventSummary;
        private final String eventType;
        private final LocalDateTime eventStart;
        private final LocalDateTime eventEnd;
        private final LocalDateTime eventCreateAt;

        @Builder
        public Data(
                Long eventIdx,
                String eventTitle,
                String eventSummary,
                String eventType,
                LocalDateTime eventStart,
                LocalDateTime eventEnd,
                LocalDateTime eventCreateAt) {
            this.eventIdx = eventIdx;
            this.eventTitle = eventTitle;
            this.eventSummary = eventSummary;
            this.eventType = eventType;
            this.eventStart = eventStart;
            this.eventEnd = eventEnd;
            this.eventCreateAt = eventCreateAt;
        }
    }
}
