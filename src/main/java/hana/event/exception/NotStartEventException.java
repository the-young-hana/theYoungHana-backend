package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class NotStartEventException extends BaseCodeException {
    public NotStartEventException() {
        super(EventHttpCode.NOT_START_EVENT);
    }
}
