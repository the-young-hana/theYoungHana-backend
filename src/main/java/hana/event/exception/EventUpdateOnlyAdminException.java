package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventUpdateOnlyAdminException extends BaseCodeException {
    public EventUpdateOnlyAdminException() {
        super(EventHttpCode.EVENT_UPDATE_ONLY_ADMIN);
    }
}
