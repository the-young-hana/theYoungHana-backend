package hana.member.exception;

import static hana.member.exception.StudentHttpCode.NOTFOUND_STUDENT;

import hana.common.exception.BaseCodeException;

public class StudentNotFoundException extends BaseCodeException {
    public StudentNotFoundException() {
        super(NOTFOUND_STUDENT);
    }
}
