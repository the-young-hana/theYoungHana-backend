package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.member.domain.Member;
import hana.member.domain.Student;
import hana.member.domain.StudentRepository;
import hana.member.dto.StudentReadResDto;
import hana.member.exception.StudentNotFoundException;
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

    @MethodInfo(name = "getStudentByMemberIdx", description = "회원 번호로 학생 정보를 조회합니다.")
    public StudentReadResDto getStudentByMemberIdx(Long memberIdx) {
        Student student = studentRepository.findByMember_MemberIdx(memberIdx);
        if (student == null) {
            throw new StudentNotFoundException();
        }

        StudentReadResDto.Data data =
                StudentReadResDto.Data.builder()
                        .studentIdx(student.getStudentIdx())
                        .studentName(student.getStudentName())
                        .studentId(student.getStudentId())
                        .studentCollege(student.getDept().getCollege().getCollegeName())
                        .studentDept(student.getDept().getDeptName())
                        .studentCardFrontImage(student.getStudentCard().getStudentCardFrontImage())
                        .studentCardBackImage(student.getStudentCard().getStudentCardBackImage())
                        .isVertical(student.getStudentCard().isStudentCardIsVertical())
                        .build();

        return StudentReadResDto.builder().data(data).build();
    }

    @Builder
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
