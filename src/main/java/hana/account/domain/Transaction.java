package hana.account.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Transaction", description = "거래 엔티티")
@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_idx", nullable = false)
    private Long transactionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_idx", nullable = false)
    private Account account;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "transaction_name", nullable = false)
    private String transactionName;

    @Column(name = "transaction_amount", nullable = false)
    private Long transactionAmount;

    @Column(name = "transaction_balance", nullable = false)
    private long transactionBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionTypeEnumType transactionTypeEnumType;

    @Builder
    public Transaction(
            Account account,
            String transactionId,
            String transactionName,
            Long transactionAmount,
            Long transactionBalance,
            TransactionTypeEnumType transactionTypeEnumType) {
        this.account = account;
        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionBalance = transactionBalance;
        this.transactionTypeEnumType = transactionTypeEnumType;
    }
}
