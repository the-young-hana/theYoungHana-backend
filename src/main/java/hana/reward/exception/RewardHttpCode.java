package hana.reward.exception;

import hana.common.dto.BaseHttpCode;
import hana.common.dto.BaseHttpReason;
import org.springframework.http.HttpStatus;

public enum RewardHttpCode implements BaseHttpCode {
    ALREADY_PARTICIPATED_QUIZ(
            HttpStatus.BAD_REQUEST, "REWARD-001", "오늘 이미 퀴즈에 참여하셨습니다. 내일 다시 도전해주세요."),
    ALREADY_PARTICIPATED_PRESENT(
            HttpStatus.BAD_REQUEST, "REWARD-002", "오늘 이미 상자를 개봉하셨습니다. 내일 다시 도전해주세요.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder().httpStatus(httpStatus).code(code).message(message).build();
    }

    RewardHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
