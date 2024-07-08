package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class InProgressEventException extends BaseCodeException {
    public InProgressEventException() {
        super(EventHttpCode.IN_PROGRESS_EVENT);
    }
}
