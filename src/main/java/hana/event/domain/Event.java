package hana.event.domain;

import hana.college.domain.Dept;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(name = "event_content", nullable = false, columnDefinition = "LONGTEXT")
    private String eventContent;

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

    @Column(name = "event_image_list", nullable = false, columnDefinition = "JSON")
    private String eventImageList;

    @Column(name = "event_limit", nullable = false)
    private Long eventLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private EventEnumType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_idx")
    private Dept dept;

    @Builder
    public Event(
            String eventTitle,
            String eventContent,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            LocalDateTime eventDatetime,
            Long eventFee,
            LocalDateTime eventFeeStartDatetime,
            LocalDateTime eventFeeEndDatetime,
            String eventImageList,
            long eventLimit,
            EventEnumType eventType,
            Dept dept) {
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventStartDatetime = eventStartDatetime;
        this.eventEndDatetime = eventEndDatetime;
        this.eventDatetime = eventDatetime;
        this.eventFee = eventFee;
        this.eventFeeStartDatetime = eventFeeStartDatetime;
        this.eventFeeEndDatetime = eventFeeEndDatetime;
        this.eventImageList = eventImageList;
        this.eventLimit = eventLimit;
        this.eventType = eventType;
        this.dept = dept;
    }
}
