package hana.college.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "College", description = "대학 엔티티")
@Entity
@Table(name = "colleges")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class College extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "college_idx", nullable = false)
    private Long collegeIdx;

    @Column(name = "college_name", nullable = false)
    private String collegeName;

    @Column(name = "college_image", nullable = false)
    private String collegeImage;

    @Builder
    public College(String collegeName, String collegeImage) {
        this.collegeName = collegeName;
        this.collegeImage = collegeImage;
    }
}
