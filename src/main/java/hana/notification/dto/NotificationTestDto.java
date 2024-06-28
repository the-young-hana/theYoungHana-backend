package hana.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationTestDto<T> {
    private final T result;
    private final int resultCode;
    private final String resultMessage;

    @Builder
    public NotificationTestDto(T result, int resultCode, String resultMessage) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
