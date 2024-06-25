package hana.college.domain;

import hana.account.domain.Account;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Dept", description = "학과 엔티티")
@Entity
@Table(name = "depts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dept extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_idx", nullable = false)
    private Long deptIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_idx", nullable = false)
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx", nullable = false)
    private Account account;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "dept_point", nullable = false)
    private Long deptPoint;

    @Column(name = "dept_account_number", nullable = false)
    private String deptAccountNumber;

    @Builder
    public Dept(
            Long deptIdx,
            College college,
            Account account,
            String deptName,
            Long deptPoint,
            String deptAccountNumber) {
        this.deptIdx = deptIdx;
        this.college = college;
        this.account = account;
        this.deptName = deptName;
        this.deptPoint = deptPoint;
        this.deptAccountNumber = deptAccountNumber;
    }
}
