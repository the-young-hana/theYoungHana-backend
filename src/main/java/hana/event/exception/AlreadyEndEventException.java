package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class AlreadyEndEventException extends BaseCodeException {
    public AlreadyEndEventException() {
        super(EventHttpCode.ALREADY_END_EVENT);
    }
}
