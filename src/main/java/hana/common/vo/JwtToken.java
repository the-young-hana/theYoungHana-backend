package hana.common.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public JwtToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
