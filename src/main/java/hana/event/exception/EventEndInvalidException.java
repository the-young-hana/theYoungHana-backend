package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventEndInvalidException extends BaseCodeException {
    public EventEndInvalidException() {
        super(EventHttpCode.EVENT_END_INVALID);
    }
}
