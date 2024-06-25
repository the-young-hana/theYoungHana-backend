package hana.member.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "NoticeRepository", description = "알림 레포지토리")
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {}
