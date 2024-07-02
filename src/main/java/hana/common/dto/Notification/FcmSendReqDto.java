package hana.common.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmSendReqDto { // request
    private final String token, title, body;

    @Builder
    public FcmSendReqDto(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
