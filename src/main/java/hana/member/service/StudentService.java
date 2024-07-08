package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.AccessDeniedCustomException;
import hana.common.utils.JwtUtils;
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
    private final JwtUtils jwtUtils;
    private final StudentRepository studentRepository;

    @MethodInfo(name = "findStudentByMemberIdx", description = "회원 번호로 학생 정보를 조회합니다.")
    public Student findStudentByMemberIdx(Member member) {

        return studentRepository.findByMember(member);
    }

    @MethodInfo(name = "getStudentByMemberIdx", description = "회원 번호로 학생 정보를 조회합니다.")
    public StudentReadResDto getStudentByMemberIdx(Long memberIdx) {
        boolean isAdmin = false;
        try {
            checkIsAdmin();
            isAdmin = true;
        } catch (AccessDeniedCustomException e) {
            isAdmin = false;
        }

        Student student = studentRepository.findByMember_MemberIdx(memberIdx);
        if (student == null) {
            throw new StudentNotFoundException();
        }

        StudentReadResDto.Data data =
                StudentReadResDto.Data.builder()
                        .studentIdx(student.getStudentIdx())
                        .memberIdx(student.getMember().getMemberIdx())
                        .studentName(student.getStudentName())
                        .studentId(student.getStudentId())
                        .studentCollege(student.getDept().getCollege().getCollegeName())
                        .studentCollegeImage(student.getDept().getCollege().getCollegeImage())
                        .studentDept(student.getDept().getDeptName())
                        .studentDeptIdx(student.getDept().getDeptIdx())
                        .studentCardFrontImage(student.getStudentCard().getStudentCardFrontImage())
                        .studentCardBackImage(student.getStudentCard().getStudentCardBackImage())
                        .isVertical(student.getStudentCard().isStudentCardIsVertical())
                        .isAdmin(isAdmin)
                        .build();

        return StudentReadResDto.builder().data(data).build();
    }

    public void checkIsAdmin() {
        if (!jwtUtils.getStudent().isStudentIsAdmin()) throw new AccessDeniedCustomException();
    }

    @Builder
    public StudentService(StudentRepository studentRepository, JwtUtils jwtUtils) {
        this.studentRepository = studentRepository;
        this.jwtUtils = jwtUtils;
    }
}
