package hana.account.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "TransactionRepository", description = "거래 레포지토리")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {}
