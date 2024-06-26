package hana.member.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum MemberHttpCode implements BaseHttpCode {
    UNLISTED_MEMBER(HttpStatus.NOT_FOUND, "MEM-001", "해당 회원이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    MemberHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
