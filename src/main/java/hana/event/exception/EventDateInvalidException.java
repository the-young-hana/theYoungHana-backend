package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventDateInvalidException extends BaseCodeException {
    public EventDateInvalidException() {
        super(EventHttpCode.EVENT_DATE_INVALID);
    }
}
