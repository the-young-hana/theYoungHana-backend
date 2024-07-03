package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "EventPrizeRepository", description = "이벤트 상품 레포지토리")
@Repository
public interface EventPrizeRepository extends JpaRepository<EventPrize, Long> {
    @MethodInfo(name = "findAllByEventIdx", description = "이벤트 인덱스로 이벤트 상품 목록을 조회합니다.")
    @Query("SELECT ep FROM EventPrize ep JOIN FETCH ep.event e WHERE e.eventIdx = :eventIdx")
    List<EventPrize> findAllByEventIdx(Long eventIdx);

    @MethodInfo(name = "findByEventIdx", description = "이벤트 인덱스로 이벤트 상품을 조회합니다.")
    @Query("SELECT ep FROM EventPrize ep JOIN FETCH ep.event e WHERE e.eventIdx = :eventIdx")
    EventPrize findByEventIdx(Long eventIdx);

    @MethodInfo(name = "deleteAllByEventIdx", description = "이벤트 인덱스로 이벤트 상품 목록을 삭제합니다.")
    @Query("UPDATE EventPrize ep SET ep.deletedYn = TRUE WHERE ep.event.eventIdx = :eventIdx")
    @Modifying
    @Transactional
    void deleteAllByEventIdx(Long eventIdx);
}
