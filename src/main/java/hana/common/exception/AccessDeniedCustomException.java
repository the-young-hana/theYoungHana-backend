package hana.common.exception;

public class AccessDeniedCustomException extends BaseCodeException {

    public AccessDeniedCustomException() {
        super(CommonHttpCode.ACCESS_DENIED);
    }
}
