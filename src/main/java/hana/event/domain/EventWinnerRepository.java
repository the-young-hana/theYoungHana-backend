package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventWinnerRepository", description = "이벤트 당첨자 레포지토리")
@Repository
public interface EventWinnerRepository extends JpaRepository<EventWinner, Long> {
    @MethodInfo(name = "findAllByEventIdx", description = "이벤트 인덱스로 이벤트 당첨자 목록을 조회합니다.")
    @Query(
            "SELECT ew FROM EventWinner ew JOIN FETCH ew.eventPrize ep JOIN FETCH ep.event e WHERE e.eventIdx = :eventIdx")
    List<EventWinner> findAllByEventIdx(Long eventIdx);

    @MethodInfo(
            name = "findByEventIdxAndStudentIdx",
            description = "이벤트 인덱스와 학생 인덱스로 이벤트 당첨자를 조회합니다.")
    @Query(
            "SELECT ew FROM EventWinner ew JOIN FETCH ew.eventPrize ep JOIN FETCH ep.event e WHERE e.eventIdx = :eventIdx AND ew.student.studentIdx = :studentIdx")
    EventWinner findByEventIdxAndStudentIdx(Long eventIdx, Long studentIdx);

    @MethodInfo(name = "countByEventIdx", description = "이벤트 인덱스로 이벤트 당첨자 수를 조회합니다.")
    @Query(
            "SELECT COUNT(ew) FROM EventWinner ew JOIN ew.eventPrize ep JOIN ep.event e WHERE e.eventIdx = :eventIdx")
    Long countByEventIdx(Long eventIdx);
}
