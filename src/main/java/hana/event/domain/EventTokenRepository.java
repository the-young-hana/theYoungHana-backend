package hana.event.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@TypeInfo(name = "EventTokenRepository", description = "이벤트 토큰 레포지토리")
@EnableRedisRepositories
public interface EventTokenRepository extends CrudRepository<EventToken, Long> {
    @MethodInfo(name = "findAllByDeptIdx", description = "학과 식별자로 이벤트 토큰 목록을 조회합니다.")
    List<EventToken> findAllByDeptIdx(Long deptIdx);
}
