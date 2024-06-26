package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.member.domain.Member;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "StudentService", description = "학생 서비스")
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @MethodInfo(name = "findStudentByMemberIdx", description = "회원 번호로 학생 정보를 조회합니다.")
    public Student findStudentByMemberIdx(Member member) {
        return studentRepository.findByMember(member);
    }

    @Builder
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
