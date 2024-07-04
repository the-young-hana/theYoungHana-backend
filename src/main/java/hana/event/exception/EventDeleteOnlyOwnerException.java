package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventDeleteOnlyOwnerException extends BaseCodeException {
    public EventDeleteOnlyOwnerException() {
        super(EventHttpCode.EVENT_DELETE_ONLY_OWNER);
    }
}
