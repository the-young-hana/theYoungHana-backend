package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventDeleteOnlyAdminException extends BaseCodeException {
    public EventDeleteOnlyAdminException() {
        super(EventHttpCode.EVENT_DELETE_ONLY_ADMIN);
    }
}
