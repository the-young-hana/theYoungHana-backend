package hana.knowledge.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum KnowledgeHttpCode implements BaseHttpCode {
    UNLISTED_KNOWLEDGE(HttpStatus.NOT_FOUND, "KNW-001", "해당 금융지식이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    KnowledgeHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
