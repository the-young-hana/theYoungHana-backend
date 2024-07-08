package hana.common.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmSendReqDto { // request
    private final String token, category, title, body;
    private final Long memberIdx;

    @Builder
    public FcmSendReqDto(String token, String category, String title, String body, Long memberIdx) {
        this.token = token;
        this.category = category;
        this.title = title;
        this.body = body;
        this.memberIdx = memberIdx;
    }
}
