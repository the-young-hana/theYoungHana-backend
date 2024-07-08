package hana.reward.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseTimeEntity;
import hana.member.domain.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "RewardHistory", description = "리워드 내역")
@Entity
@Table(name = "reward_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_history_idx", nullable = false)
    private Long rewardHistoryIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RewardHistoryCategoryEnumType rewardCategory;

    @Builder
    public RewardHistory(
            Long rewardHistoryIdx, Student student, RewardHistoryCategoryEnumType rewardCategory) {
        this.rewardHistoryIdx = rewardHistoryIdx;
        this.student = student;
        this.rewardCategory = rewardCategory;
    }
}
