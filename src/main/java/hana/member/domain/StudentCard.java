package hana.member.domain;

import hana.college.domain.College;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "StudentCard", description = "학생증 엔티티")
@Entity
@Table(name = "student_cards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_card_idx", nullable = false)
    private Long studentCardIdx;

    @Column(name = "student_card_name", nullable = false)
    private String studentCardName;

    @Column(
            name = "student_card_is_vertical",
            nullable = false,
            columnDefinition = "TINYINT(1) default 0")
    private boolean studentCardIsVertical;

    @Column(name = "student_card_front_image", nullable = false)
    private String studentCardFrontImage;

    @Column(name = "student_card_back_image", nullable = false)
    private String studentCardBackImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_idx", nullable = false)
    private College college;
}
