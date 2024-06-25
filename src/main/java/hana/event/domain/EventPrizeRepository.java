package hana.event.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventPrizeRepository", description = "이벤트 상품 레포지토리")
@Repository
public interface EventPrizeRepository extends JpaRepository<EventPrize, Long> {}
