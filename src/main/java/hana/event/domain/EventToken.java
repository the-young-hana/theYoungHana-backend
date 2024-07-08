package hana.event.domain;

import hana.common.annotation.TypeInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@TypeInfo(name = "EventToken", description = "회원 이벤트 토큰")
@RedisHash(value = "event_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventToken {
    @Id private Long eventTokenIdx;

    @Indexed private Long memberIdx;

    @Indexed private Long studentIdx;

    @Indexed private Long deptIdx;

    @Indexed private Long eventIdx;

    @Indexed private String fcmToken;

    @TimeToLive private Long ttl;

    @Builder
    public EventToken(
            Long memberIdx,
            Long studentIdx,
            Long deptIdx,
            Long eventIdx,
            String fcmToken,
            Long ttl) {
        this.memberIdx = memberIdx;
        this.studentIdx = studentIdx;
        this.deptIdx = deptIdx;
        this.eventIdx = eventIdx;
        this.fcmToken = fcmToken;
        this.ttl = ttl;
    }
}
