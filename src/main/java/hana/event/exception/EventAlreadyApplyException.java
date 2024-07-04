package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventAlreadyApplyException extends BaseCodeException {
    public EventAlreadyApplyException() {
        super(EventHttpCode.EVENT_ALREADY_APPLY);
    }
}
