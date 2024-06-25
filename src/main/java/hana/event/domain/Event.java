package hana.event.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Event", description = "이벤트 엔티티")
@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_idx", nullable = false)
    private Long eventIdx;

    @Column(name = "event_title", nullable = false, length = 50)
    private String eventTitle;

    @Column(name = "event_start_datetime", nullable = false)
    private LocalDateTime eventStartDatetime;

    @Column(name = "event_end_datetime", nullable = false)
    private LocalDateTime eventEndDatetime;

    @Column(name = "event_datetime", nullable = false)
    private LocalDateTime eventDatetime;

    @Column(name = "event_fee", nullable = false)
    private Long eventFee;

    @Column(name = "event_fee_start_datetime", nullable = false)
    private LocalDateTime eventFeeStartDatetime;

    @Column(name = "event_fee_end_datetime", nullable = false)
    private LocalDateTime eventFeeEndDatetime;

    @Column(name = "event_image", nullable = false, columnDefinition = "JSON")
    private String eventImageList;

    @Column(name = "event_limit", nullable = false)
    private long eventLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private EventEnumType eventType;
}
