package hana.story.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionDetailRepository", description = "거래 내역 레포지토리")
@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {}
