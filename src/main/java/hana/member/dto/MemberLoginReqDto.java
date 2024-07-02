package hana.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginReqDto {
    private String password;
    private String fcmToken;

    @Builder
    public MemberLoginReqDto(String password, String fcmToken) {
        this.password = password;
        this.fcmToken = fcmToken;
    }
}
