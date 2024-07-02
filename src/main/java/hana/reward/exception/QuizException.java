package hana.reward.exception;

import hana.common.exception.BaseCodeException;

public class QuizException extends BaseCodeException {
    public QuizException() {
        super(RewardHttpCode.ALREADY_PARTICIPATED_QUIZ);
    }
}