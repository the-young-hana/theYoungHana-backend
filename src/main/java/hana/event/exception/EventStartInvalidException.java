package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventStartInvalidException extends BaseCodeException {
    public EventStartInvalidException() {
        super(EventHttpCode.EVENT_START_INVALID);
    }
}
