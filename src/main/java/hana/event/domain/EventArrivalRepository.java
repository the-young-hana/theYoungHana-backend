package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventArrivalRepository", description = "이벤트 신청자 레포지토리")
@Repository
public interface EventArrivalRepository extends JpaRepository<EventArrival, Long> {
    @MethodInfo(name = "findAllByEventIdx", description = "이벤트 인덱스로 이벤트 신청자 목록을 조회합니다.")
    @Query("SELECT ea FROM EventArrival ea JOIN FETCH ea.event e WHERE e.eventIdx = :eventIdx")
    List<EventArrival> findAllByEventIdx(Long eventIdx);

    @MethodInfo(
            name = "findByEventIdxAndStudentIdx",
            description = "이벤트 인덱스와 학생 인덱스로 이벤트 신청자를 조회합니다.")
    @Query(
            "SELECT ea FROM EventArrival ea JOIN FETCH ea.event e WHERE e.eventIdx = :eventIdx AND ea.student.studentIdx = :studentIdx")
    EventArrival findByEventIdxAndStudentIdx(Long eventIdx, Long studentIdx);
}
