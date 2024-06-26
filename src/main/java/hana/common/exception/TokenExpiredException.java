package hana.common.exception;

public class TokenExpiredException extends BaseCodeException {

    public TokenExpiredException() {
        super(CommonHttpCode.TOKEN_EXPIRED);
    }
}
