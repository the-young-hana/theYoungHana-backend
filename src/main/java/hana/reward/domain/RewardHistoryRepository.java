package hana.reward.domain;

import hana.common.annotation.TypeInfo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "RewardHistoryRepository", description = "리워드 참여내역 레포지토리")
@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

    @Query(
            value =
                    "SELECT * FROM reward_histories r WHERE student_idx = :studentIdx AND DATE(created_at) = :date",
            nativeQuery = true)
    List<RewardHistory> findHistoryByDate(Long studentIdx, LocalDate date);

    @Query(
            value = "SELECT * FROM reward_histories r WHERE student_idx = :studentIdx",
            nativeQuery = true)
    List<RewardHistory> testList(Long studentIdx);
}
