package hana.event.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import hana.member.domain.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "EventWinner", description = "이벤트 당첨자")
@Entity
@Table(name = "event_winners")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventWinner extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_winner_idx", nullable = false)
    private Long eventWinnerIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_prize_idx", nullable = false)
    private EventPrize eventPrize;

    @Builder
    public EventWinner(Student student, EventPrize eventPrize) {
        this.student = student;
        this.eventPrize = eventPrize;
    }
}
