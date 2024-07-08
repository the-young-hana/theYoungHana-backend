package hana.member.exception;

import hana.common.exception.BaseCodeException;

public class UnlistedMemberException extends BaseCodeException {
    public UnlistedMemberException() {
        super(MemberHttpCode.UNLISTED_MEMBER);
    }
}
