package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventRepository", description = "이벤트 레포지토리")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @MethodInfo(name = "findByEventIdx", description = "이벤트를 조회합니다.")
    @Query("SELECT e FROM Event e WHERE e.eventIdx = :eventIdx AND e.deletedYn = false")
    Event findByEventIdx(Long eventIdx);

    @MethodInfo(name = "searchEventsAfterIsEnd", description = "종료된 이벤트 목록을 검색합니다.")
    @Query(
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime > CURRENT_TIMESTAMP AND e.deletedYn = false")
    List<Event> searchEventsAfterIsEnd(String value, Pageable pageable);

    @MethodInfo(name = "searchEventsBeforeIsEnd", description = "진행 중인 이벤트 목록을 검색합니다.")
    @Query(
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime < CURRENT_TIMESTAMP AND e.deletedYn = false")
    List<Event> searchEventsBeforeIsEnd(String value, Pageable pageable);
}
