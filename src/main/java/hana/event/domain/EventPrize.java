package hana.event.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "EventPrize", description = "이벤트 상품 엔티티")
@Entity
@Table(name = "event_prizes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventPrize extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_prize_idx", nullable = false)
    private Long eventPrizeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_idx", nullable = false)
    private Event event;

    @Column(name = "event_prize_rank", nullable = false)
    private long eventPrizeRank;

    @Column(name = "event_prize_name", nullable = false)
    private String eventPrizeName;

    @Column(name = "event_prize_limit", nullable = false)
    private long eventPrizeLimit;

    @Builder
    public EventPrize(
            Event event, long eventPrizeRank, String eventPrizeName, long eventPrizeLimit) {
        this.event = event;
        this.eventPrizeRank = eventPrizeRank;
        this.eventPrizeName = eventPrizeName;
        this.eventPrizeLimit = eventPrizeLimit;
    }

    public void setEventPrizeLimit(long eventPrizeLimit) {
        this.eventPrizeLimit = eventPrizeLimit;
    }
}
