package hana.event.domain;

import hana.common.annotation.TypeInfo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@TypeInfo(name = "ScheduledEvent", description = "예약 이벤트 엔티티")
@RedisHash(value = "scheduled_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduledEvent {
    @Id private Long scheduledEventIdx;

    @Indexed private Long eventIdx;

    @Indexed private String eventTitle;

    @Indexed private Long deptIdx;

    @Indexed
    @Enumerated(EnumType.STRING)
    private ScheduledEventEnumType scheduledEventType;

    @Indexed private LocalDateTime eventStartDatetime;

    @Indexed private LocalDateTime eventEndDatetime;

    @Indexed private LocalDateTime eventDatetime;

    @Indexed private Long eventFee;

    @Indexed private LocalDateTime eventFeeStartDatetime;

    @Indexed private LocalDateTime eventFeeEndDatetime;

    @Indexed private Long eventLimit;

    @Indexed private String scheduledDatetime;

    @Builder
    public ScheduledEvent(
            Long eventIdx,
            String eventTitle,
            Long deptIdx,
            ScheduledEventEnumType scheduledEventType,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            LocalDateTime eventDatetime,
            Long eventFee,
            LocalDateTime eventFeeStartDatetime,
            LocalDateTime eventFeeEndDatetime,
            Long eventLimit,
            String scheduledDatetime) {
        this.eventIdx = eventIdx;
        this.eventTitle = eventTitle;
        this.deptIdx = deptIdx;
        this.scheduledEventType = scheduledEventType;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.eventDatetime = eventDatetime;
        this.eventFee = eventFee;
        this.eventFeeStartDatetime = eventFeeStartDatetime;
        this.eventFeeEndDatetime = eventFeeEndDatetime;
        this.eventLimit = eventLimit;
        this.scheduledDatetime = scheduledDatetime;
    }
}
