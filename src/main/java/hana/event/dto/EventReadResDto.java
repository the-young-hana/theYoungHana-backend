package hana.event.dto;

import hana.common.dto.BaseResponse;
import hana.event.domain.EventEnumType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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
        private final Long eventIdx;
        private final Integer isEnd;
        private final Boolean isMine;
        private final EventEnumType eventType;
        private final String eventTitle;
        private final LocalDateTime eventStart;
        private final LocalDateTime eventEnd;
        private final LocalDateTime eventDt;
        private final Long eventFee;
        private final LocalDateTime eventFeeStart;
        private final LocalDateTime eventFeeEnd;
        private final String eventContent;
        private final List<MultipartFile> eventImageList;
        private final Long eventLimit;
        private final List<EventPrize> eventPrizeList;

        @Builder
        public Data(
                Long eventIdx,
                Integer isEnd,
                Boolean isMine,
                EventEnumType eventType,
                String eventTitle,
                LocalDateTime eventStart,
                LocalDateTime eventEnd,
                LocalDateTime eventDt,
                Long eventFee,
                LocalDateTime eventFeeStart,
                LocalDateTime eventFeeEnd,
                String eventContent,
                List<MultipartFile> eventImageList,
                Long eventLimit,
                List<EventPrize> eventPrizeList) {
            this.eventIdx = eventIdx;
            this.isEnd = isEnd;
            this.isMine = isMine;
            this.eventType = eventType;
            this.eventTitle = eventTitle;
            this.eventStart = eventStart;
            this.eventEnd = eventEnd;
            this.eventDt = eventDt;
            this.eventFee = eventFee;
            this.eventFeeStart = eventFeeStart;
            this.eventFeeEnd = eventFeeEnd;
            this.eventContent = eventContent;
            this.eventImageList = eventImageList;
            this.eventLimit = eventLimit;
            this.eventPrizeList = eventPrizeList;
        }

        @Getter
        public static class EventPrize {
            private final Long prizeRank;
            private final String prizeName;
            private final Long prizeLimit;

            @Builder
            public EventPrize(Long prizeRank, String prizeName, Long prizeLimit) {
                this.prizeRank = prizeRank;
                this.prizeName = prizeName;
                this.prizeLimit = prizeLimit;
            }
        }
    }
}
