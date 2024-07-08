package hana.member.domain;

import hana.common.annotation.TypeInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@TypeInfo(name = "MemberToken", description = "회원 토큰")
@RedisHash(value = "member_tokens")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberToken {
    @Id private Long memberTokenIdx;

    @Indexed private Long memberIdx;

    @Indexed private Long studentIdx;

    @Indexed private Long deptIdx;

    @Indexed private String fcmToken;

    @Indexed private String accessToken;

    @Indexed private String refreshToken;

    @TimeToLive private Long ttl;

    @Builder
    public MemberToken(
            Long memberTokenIdx,
            Long memberIdx,
            Long studentIdx,
            Long deptIdx,
            String fcmToken,
            String accessToken,
            String refreshToken,
            Long ttl) {
        this.memberTokenIdx = memberTokenIdx;
        this.memberIdx = memberIdx;
        this.studentIdx = studentIdx;
        this.deptIdx = deptIdx;
        this.fcmToken = fcmToken;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }
}
