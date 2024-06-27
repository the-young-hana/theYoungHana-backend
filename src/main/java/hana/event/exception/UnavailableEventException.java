package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class UnavailableEventException extends BaseCodeException {
    public UnavailableEventException() {
        super(EventHttpCode.UNAVAILABLE_EVENT);
    }
}
