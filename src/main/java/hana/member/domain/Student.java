package hana.member.domain;

import hana.college.domain.Dept;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Student", description = "학생 엔티티")
@Entity
@Table(name = "students")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_idx", nullable = false)
    private Long studentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_idx", nullable = false)
    private Dept dept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_card_idx", nullable = false)
    private StudentCard studentCard;

    @Column(name = "student_name", nullable = false, length = 255)
    private String studentName;

    @Column(name = "student_nickname", nullable = false, length = 50)
    private String studentNickname;

    @Column(name = "student_id", nullable = false, length = 255)
    private String studentId;

    @Column(name = "student_point", nullable = false)
    private int studentPoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status", nullable = false, length = 10)
    private StudentStatusEnumType studentStatus;

    @Column(name = "student_is_admin", nullable = false)
    private boolean studentIsAdmin;

    @Column(name = "student_grade", nullable = false)
    private int studentGrade;

    public boolean getStudentIsAdmin() {
        return studentIsAdmin;
    }
}
