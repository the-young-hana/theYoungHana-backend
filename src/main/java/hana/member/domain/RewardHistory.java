package hana.member.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "RewardHistory", description = "리워드 내역")
@Entity
@Table(name = "reward_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_histories_idx", nullable = false)
    private Long rewardHistoriesIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_idx", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RewardCategoryEnumType rewardCategory;
}
