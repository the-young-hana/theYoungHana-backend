package hana.event.domain;

import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventRepository", description = "이벤트 레포지토리")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime > CURRENT_TIMESTAMP")
    List<Event> searchEventsAfterIsEnd(String value, Pageable pageable);

    @Query(
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime < CURRENT_TIMESTAMP")
    List<Event> searchEventsBeforeIsEnd(String value, Pageable pageable);
}
