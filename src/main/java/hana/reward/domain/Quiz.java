package hana.reward.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Quiz", description = "퀴즈 엔티티")
@Entity
@Table(name = "quizzes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_idx", nullable = false)
    private Long quizIdx;

    @Column(name = "quiz_content", nullable = false)
    private String quizContent;

    @Column(name = "quiz_answer", nullable = false)
    private int quizAnswer;

    @Column(name = "quiz_explanation", nullable = false)
    private String quizExplanation;
}
