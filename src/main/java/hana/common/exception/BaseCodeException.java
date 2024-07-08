package hana.common.exception;

import hana.common.dto.BaseHttpCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseCodeException extends RuntimeException {
    private final BaseHttpCode baseHttpCode;

    @Builder
    public BaseCodeException(BaseHttpCode baseHttpCode) {
        super(baseHttpCode.getHttpReason().getMessage());
        this.baseHttpCode = baseHttpCode;
    }
}
