package hana.event.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventWinnerRepository", description = "이벤트 당첨자 레포지토리")
@Repository
public interface EventWinnerRepository extends JpaRepository<EventWinner, Long> {}
