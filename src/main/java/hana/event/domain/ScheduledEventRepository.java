package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@TypeInfo(name = "ScheduledEventRepository", description = "예약 이벤트 레포지토리")
@EnableRedisRepositories
public interface ScheduledEventRepository extends CrudRepository<ScheduledEvent, String> {
    @MethodInfo(name = "findAllByScheduledDatetime", description = "예약 일시로 예약 이벤트를 조회합니다.")
    List<ScheduledEvent> findAllByScheduledDatetime(String scheduledDatetime);

    @MethodInfo(name = "deleteAllByEventIdx", description = "이벤트 인덱스로 예약 이벤트를 삭제합니다.")
    void deleteAllByEventIdx(Long eventIdx);
}
