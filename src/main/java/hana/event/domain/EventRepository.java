package hana.event.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventRepository", description = "이벤트 레포지토리")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {}
