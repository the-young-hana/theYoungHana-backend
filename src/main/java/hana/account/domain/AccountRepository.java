package hana.account.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "AccountRepository", description = "계좌 레포지토리")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {}
