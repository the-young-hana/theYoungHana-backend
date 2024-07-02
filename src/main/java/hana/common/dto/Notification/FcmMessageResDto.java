package hana.common.dto.notification;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FcmMessageResDto { // response
    private final boolean validateOnly;
    private final FcmMessageResDto.Message message;

    @Getter
    @Builder
    public static class Message {
        private FcmMessageResDto.Notification notification;
        private String token;
    }

    @Builder
    @Getter
    public static class Notification {
        private String title, body, image;
    }

    @Builder
    public FcmMessageResDto(boolean validateOnly, Message message) {
        this.validateOnly = validateOnly;
        this.message = message;
    }
}
