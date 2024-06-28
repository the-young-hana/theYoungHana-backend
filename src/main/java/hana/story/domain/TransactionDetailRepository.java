package hana.story.domain;

import hana.account.domain.Transaction;
import hana.common.annotation.TypeInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionDetailRepository", description = "거래 내역 레포지토리")
@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    Optional<TransactionDetail> findByTransaction(Transaction transaction);

    void deleteByStory_StoryIdx(Long storyIdx);
}
