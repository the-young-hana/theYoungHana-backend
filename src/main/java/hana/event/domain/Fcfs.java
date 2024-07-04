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

@TypeInfo(name = "Fcfs", description = "선착순")
@RedisHash(value = "fcfs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fcfs {
    @Id private Long fcfsIdx;
    @Indexed private Long eventIdx;

    @Indexed private Long memberIdx;

    @TimeToLive private Long ttl;

    @Builder
    public Fcfs(Long eventIdx, Long memberIdx, Long ttl) {
        this.eventIdx = eventIdx;
        this.memberIdx = memberIdx;
        this.ttl = ttl;
    }
}
