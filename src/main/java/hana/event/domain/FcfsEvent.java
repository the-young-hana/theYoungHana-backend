package hana.event.domain;

import hana.common.annotation.TypeInfo;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@TypeInfo(name = "FcfsEvent", description = "선착순 이벤트")
@RedisHash(value = "fcfs_event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcfsEvent {
    @Id private Long fcfsIdx;

    @Indexed private Long eventIdx;

    @Indexed private String eventTitle;

    @Indexed private Long eventLimit;

    @Indexed private Long eventCount;

    @TimeToLive private Long ttl;

    @Builder
    public FcfsEvent(Long eventIdx, String eventTitle, Long eventLimit, Long eventCount, Long ttl) {
        this.eventIdx = eventIdx;
        this.eventTitle = eventTitle;
        this.eventLimit = eventLimit;
        this.eventCount = eventCount;
        this.ttl = ttl;
    }
}
