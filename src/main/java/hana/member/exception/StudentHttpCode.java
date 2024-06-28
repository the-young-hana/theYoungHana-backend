package hana.member.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum StudentHttpCode implements BaseHttpCode {
    NOTFOUND_STUDENT(HttpStatus.NOT_FOUND, "STU-001", "해당 학생이 조회되지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    StudentHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
