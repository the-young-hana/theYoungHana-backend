package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class EventFeeEndInvalidException extends BaseCodeException {
    public EventFeeEndInvalidException() {
        super(EventHttpCode.EVENT_FEE_END_INVALID);
    }
}
