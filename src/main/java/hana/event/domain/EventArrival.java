package hana.event.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import hana.member.domain.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "EventArrival", description = "이벤트 신청자 엔티티")
@Entity
@Table(name = "event_arrivals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventArrival extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_arrival_idx", nullable = false)
    private Long eventArrivalIdx;

    @Column(name = "event_arrival_is_winner", nullable = false)
    private Boolean isWinner = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_idx", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx", nullable = false)
    private Student student;

    @Builder
    public EventArrival(Long eventArrivalIdx, Boolean isWinner, Event event, Student student) {
        this.eventArrivalIdx = eventArrivalIdx;
        this.isWinner = isWinner;
        this.event = event;
        this.student = student;
    }

    public void setIsWinner(Boolean isWinner) {
        this.isWinner = isWinner;
    }
}
