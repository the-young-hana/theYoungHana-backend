package hana.common.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum CommonHttpCode implements BaseHttpCode {
    TOKEN_HAS_UNKNOWN_MEMBER(
            HttpStatus.UNAUTHORIZED, "JWT-001", "유효하지 않은 사용자를 토큰으로 인증하려고 시도하였습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT-002", "토큰이 만료되었습니다."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS-DENIED-01", "접근이 거부되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    CommonHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
