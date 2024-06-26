package hana.common.exception;

public class TokenHasUnknownMemberException extends BaseCodeException {

    public TokenHasUnknownMemberException() {
        super(CommonHttpCode.TOKEN_HAS_UNKNOWN_MEMBER);
    }
}
