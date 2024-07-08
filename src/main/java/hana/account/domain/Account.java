package hana.account.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import hana.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@TypeInfo(name = "Account", description = "계좌 엔티티")
@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_idx", nullable = false)
    private Long accountIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @Column(name = "account_number", nullable = false, length = 50, unique = true)
    private String accountNumber;

    @Column(name = "account_balance", nullable = false)
    private Long accountBalance;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_pw", nullable = false)
    private String accountPw;

    @Builder
    public Account(
            Long accountIdx,
            Member member,
            String accountNumber,
            Long accountBalance,
            String accountName,
            String accountPw) {
        this.accountIdx = accountIdx;
        this.member = member;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.accountName = accountName;
        this.accountPw = accountPw;
    }

    // 입금
    public void deposit(Long amount) {
        this.accountBalance += amount;
    }

    // 출금
    public void withdraw(Long amount) {
        this.accountBalance -= amount;
    }
}
