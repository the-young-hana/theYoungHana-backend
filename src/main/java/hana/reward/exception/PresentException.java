package hana.reward.exception;

import hana.common.exception.BaseCodeException;

public class PresentException extends BaseCodeException {
    public PresentException() {
        super(RewardHttpCode.ALREADY_PARTICIPATED_PRESENT);
    }
}
