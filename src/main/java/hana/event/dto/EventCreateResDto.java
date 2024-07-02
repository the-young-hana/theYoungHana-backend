package hana.event.dto;

import hana.common.dto.BaseResponse;
import hana.event.domain.EventEnumType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventCreateResDto extends BaseResponse {
    private final Data data;

    @Builder
    public EventCreateResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String eventTitle;
        private final Long eventFee;
        private final String eventContent;
        private final List<String> eventImageList;
        private final LocalDateTime eventStart;
        private final LocalDateTime eventEnd;
        private final Integer isEnd;
        private final Boolean isMine;
        private final EventEnumType eventType;

        @Builder
        public Data(
                String eventTitle,
                Long eventFee,
                String eventContent,
                List<String> eventImageList,
                LocalDateTime eventStart,
                LocalDateTime eventEnd,
                Integer isEnd,
                Boolean isMine,
                EventEnumType eventType) {
            this.eventTitle = eventTitle;
            this.eventFee = eventFee;
            this.eventContent = eventContent;
            this.eventImageList = eventImageList;
            this.eventStart = eventStart;
            this.eventEnd = eventEnd;
            this.isEnd = isEnd;
            this.isMine = isMine;
            this.eventType = eventType;
        }
    }
}
