package hana.member.exception;

import hana.common.exception.BaseCodeException;

import static hana.member.exception.StudentHttpCode.NOTFOUND_STUDENT;

public class StudentNotFoundException extends BaseCodeException {
    public StudentNotFoundException() {
        super(NOTFOUND_STUDENT);
    }
}
