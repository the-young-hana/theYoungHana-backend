package hana.event.exception;

import hana.common.exception.BaseCodeException;

public class DeptNotMatchException extends BaseCodeException {
    public DeptNotMatchException() {
        super(EventHttpCode.DEPT_NOT_MATCH);
    }
}
