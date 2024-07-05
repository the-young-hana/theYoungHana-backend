package hana.account.domain;

import hana.common.annotation.TypeInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "AccountRepository", description = "계좌 레포지토리")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByMember_MemberIdxAndDeletedYnFalse(Long memberIdx);
}
