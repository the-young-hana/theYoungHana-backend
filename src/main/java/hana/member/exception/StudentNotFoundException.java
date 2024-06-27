package hana.member.exception;

import hana.common.exception.BaseCodeException;

public class StudentNotFoundException extends BaseCodeException {
    public StudentNotFoundException() {
        super(StudentHttpCode.NOTFOUND_STUDENT);
    }
}
