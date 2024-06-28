package hana.event.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EventReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public EventReadResDto(Data data) {
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

        @Builder
        public Data(
                String eventTitle,
                Long eventFee,
                String eventContent,
                List<String> eventImageList,
                LocalDateTime eventStart,
                LocalDateTime eventEnd,
                Integer isEnd,
                Boolean isMine) {
            this.eventTitle = eventTitle;
            this.eventFee = eventFee;
            this.eventContent = eventContent;
            this.eventImageList = eventImageList;
            this.eventStart = eventStart;
            this.eventEnd = eventEnd;
            this.isEnd = isEnd;
            this.isMine = isMine;
        }
    }
}
