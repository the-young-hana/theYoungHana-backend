package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventCreateOnlyAdminException extends BaseCodeException {
    public EventCreateOnlyAdminException() {
        super(EventHttpCode.EVENT_CREATE_ONLY_ADMIN);
    }
}
