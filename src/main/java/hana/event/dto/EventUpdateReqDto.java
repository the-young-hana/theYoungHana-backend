package hana.event.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventUpdateReqDto {
    private String eventTitle;
    private String eventType;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private LocalDateTime eventDt;
    private Long eventFee;
    private LocalDateTime eventFeeStart;
    private LocalDateTime eventFeeEnd;
    private String eventContent;
    private Long eventLimit;
    private List<EventPrize> eventPrizeList;

    @Builder
    public EventUpdateReqDto(
            String eventTitle,
            String eventType,
            LocalDateTime eventStart,
            LocalDateTime eventEnd,
            LocalDateTime eventDt,
            Long eventFee,
            LocalDateTime eventFeeStart,
            LocalDateTime eventFeeEnd,
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
