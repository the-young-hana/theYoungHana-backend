package hana.reward.domain;

import hana.common.annotation.TypeInfo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "RewardHistoryRepository", description = "리워드 참여내역 레포지토리")
@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

    @Query(
            "SELECT r FROM RewardHistory r WHERE r.student.studentIdx = :studentIdx AND r.createdAt BETWEEN :startOfDay AND :endOfDay")
    List<RewardHistory> findHistoryByDate(
            @Param("studentIdx") Long studentIdx,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query(
            value = "SELECT * FROM reward_histories r WHERE student_idx = :studentIdx",
            nativeQuery = true)
    List<RewardHistory> testList(Long studentIdx);
}
