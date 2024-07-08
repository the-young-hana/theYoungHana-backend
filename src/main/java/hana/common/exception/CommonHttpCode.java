package hana.common.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum CommonHttpCode implements BaseHttpCode {
    // 토큰 관련
    TOKEN_HAS_UNKNOWN_MEMBER(
            HttpStatus.UNAUTHORIZED, "JWT-001", "유효하지 않은 사용자를 토큰으로 인증하려고 시도하였습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT-002", "토큰이 만료되었습니다."),
    // 인증 관련
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS-DENIED-01", "접근이 거부되었습니다."),
    // 페이지 관련
    PAGE_START_INDEX(HttpStatus.BAD_REQUEST, "PAGE-001", "페이지 시작 인덱스는 0보다 커야 합니다.");

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
