package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventFeeStartInvalidException extends BaseCodeException {
    public EventFeeStartInvalidException() {
        super(EventHttpCode.EVENT_FEE_START_INVALID);
    }
}
