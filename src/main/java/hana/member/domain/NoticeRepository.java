package hana.member.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "NoticeRepository", description = "알림 레포지토리")
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @MethodInfo(name = "findByMemberIdx", description = "회원 번호로 알림을 조회합니다.")
    @Query("SELECT n FROM Notice n WHERE n.member.memberIdx = :memberIdx")
    List<Notice> findByMemberIdx(Long memberIdx);
}
