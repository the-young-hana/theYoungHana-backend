package hana.story.domain;

import hana.account.domain.Transaction;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "TransactionDetail", description = "거래 내역")
@Entity
@Table(name = "transaction_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_detail_idx", nullable = false)
    private Long transactionDetailIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_idx", unique = true)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_idx", nullable = false)
    private Story story;

    @Builder
    public TransactionDetail(Transaction transaction, Story story) {
        this.transaction = transaction;
        this.story = story;
    }
}
