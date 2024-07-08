package hana.event.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventUpdateReqDto {
    private String eventTitle;
    private String eventType;
    private String eventStart;
    private String eventEnd;
    private String eventDt;
    private Long eventFee;
    private String eventFeeStart;
    private String eventFeeEnd;
    private String eventContent;
    private Long eventLimit;
    private List<EventPrize> eventPrizeList;

    @Builder
    public EventUpdateReqDto(
            String eventTitle,
            String eventType,
            String eventStart,
            String eventEnd,
            String eventDt,
            Long eventFee,
            String eventFeeStart,
            String eventFeeEnd,
            String eventContent,
            Long eventLimit,
            List<EventPrize> eventPrizeList) {
        this.eventTitle = eventTitle;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.eventDt = eventDt;
        this.eventFee = eventFee;
        this.eventFeeStart = eventFeeStart;
        this.eventFeeEnd = eventFeeEnd;
        this.eventContent = eventContent;
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
