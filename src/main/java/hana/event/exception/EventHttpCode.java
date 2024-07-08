package hana.event.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum EventHttpCode implements BaseHttpCode {
    UNAVAILABLE_EVENT(HttpStatus.NOT_FOUND, "EVT-001", "해당 이벤트가 존재하지 않습니다."),
    IN_PROGRESS_EVENT(HttpStatus.BAD_REQUEST, "EVT-002", "현재 진행 중이거나 종료된 이벤트입니다."),
    DEPT_NOT_MATCH(HttpStatus.BAD_REQUEST, "EVT-003", "해당 학과에 속한 이벤트가 아닙니다."),
    EVENT_START_INVALID(HttpStatus.BAD_REQUEST, "EVT-004", "이벤트 시작일은 현재 시간 이후여야 합니다."),
    EVENT_END_INVALID(HttpStatus.BAD_REQUEST, "EVT-005", "이벤트 종료일은 시작일 이후여야 합니다."),
    EVENT_DATE_INVALID(HttpStatus.BAD_REQUEST, "EVT-006", "이벤트 발표일은 이벤트 종료일 이후여야 합니다."),
    EVENT_FEE_START_INVALID(HttpStatus.BAD_REQUEST, "EVT-007", "이벤트 입금 시작일은 이벤트 종료일 이후여야 합니다."),
    EVENT_FEE_END_INVALID(HttpStatus.BAD_REQUEST, "EVT-008", "이벤트 입금 종료일은 입금 시작일 이후여야 합니다."),
    EVENT_DELETE_ONLY_ADMIN(HttpStatus.BAD_REQUEST, "EVT-009", "이벤트를 삭제할 권한이 없습니다."),
    EVENT_ALREADY_APPLY(HttpStatus.BAD_REQUEST, "EVT-010", "이미 해당 이벤트에 참가 신청을 하였습니다."),
    ALREADY_END_EVENT(HttpStatus.BAD_REQUEST, "EVT-011", "이미 종료된 이벤트입니다."),
    NOT_START_EVENT(HttpStatus.BAD_REQUEST, "EVT-012", "아직 시작되지 않은 이벤트입니다."),
    EVENT_CREATE_ONLY_ADMIN(HttpStatus.BAD_REQUEST, "EVT-013", "이벤트를 생성할 권한이 없습니다."),
    EVENT_UPDATE_ONLY_ADMIN(HttpStatus.BAD_REQUEST, "EVT-014", "이벤트를 수정할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    EventHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
