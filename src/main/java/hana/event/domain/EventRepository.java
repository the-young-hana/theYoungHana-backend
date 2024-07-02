package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime > CURRENT_TIMESTAMP AND e.deletedYn = false AND e.dept.deptIdx = :deptIdx ORDER BY e.eventIdx DESC")
    List<Event> searchEventsAfterIsEnd(String value, Pageable pageable, Long deptIdx);

    @MethodInfo(name = "searchEventsBeforeIsEnd", description = "진행 중인 이벤트 목록을 검색합니다.")
    @Query(
            "SELECT e FROM Event e WHERE e.eventTitle LIKE %:value% AND e.eventEndDatetime <= CURRENT_TIMESTAMP AND e.deletedYn = false AND e.dept.deptIdx = :deptIdx ORDER BY e.eventIdx DESC")
    List<Event> searchEventsBeforeIsEnd(String value, Pageable pageable, Long deptIdx);

    @MethodInfo(name = "findEventsAfterIsEnd", description = "종료된 이벤트 목록을 조회합니다.")
    @Query(
            "SELECT e FROM Event e WHERE e.eventEndDatetime > CURRENT_TIMESTAMP AND e.deletedYn = false AND e.dept.deptIdx = :deptIdx ORDER BY e.eventIdx DESC")
    List<Event> findEventsAfterIsEnd(Pageable pageable, Long deptIdx);

    @MethodInfo(name = "findEventsBeforeIsEnd", description = "진행 중인 이벤트 목록을 조회합니다.")
    @Query(
            "SELECT e FROM Event e WHERE e.eventEndDatetime <= CURRENT_TIMESTAMP AND e.deletedYn = false AND e.dept.deptIdx = :deptIdx ORDER BY e.eventIdx DESC")
    List<Event> findEventsBeforeIsEnd(Pageable pageable, Long deptIdx);

    @MethodInfo(name = "update", description = "이벤트를 수정합니다.")
    @Modifying
    @Transactional
    @Query(
            "UPDATE Event e SET e.eventTitle = :#{#event.eventTitle}, e.eventContent = :#{#event.eventContent},"
                    + "e.eventStartDatetime = :#{#event.eventStartDatetime}, e.eventEndDatetime = :#{#event.eventEndDatetime},"
                    + "e.eventDatetime = :#{#event.eventDatetime}, e.eventFee = :#{#event.eventFee},"
                    + "e.eventFeeStartDatetime = :#{#event.eventFeeStartDatetime}, e.eventFeeEndDatetime = :#{#event.eventFeeEndDatetime},"
                    + "e.eventImageList = :#{#event.eventImageList}, e.eventLimit = :#{#event.eventLimit},"
                    + "e.eventType = :#{#event.eventType},"
                    + "e.dept = :#{#event.dept} WHERE e.eventIdx = :eventId")
    void update(Long eventId, Event event);

    @MethodInfo(name = "delete", description = "이벤트를 삭제합니다.")
    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.deletedYn = true WHERE e.eventIdx = :eventIdx")
    void delete(Long eventIdx);
}
