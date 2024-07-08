package hana.member.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "StudentRepository", description = "학생 레포지토리")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByMember(Member member);

    Student findByMember_MemberIdx(Long memberIdx);

    @Query(
            value = "SELECT student_point FROM students WHERE student_idx = :studentIdx",
            nativeQuery = true)
    Long findStudentPointByStudentIdx(Long studentIdx);

    @Modifying
    @Transactional
    @Query(
            value =
                    "UPDATE students SET student_point = student_point + :points WHERE student_idx = :studentIdx",
            nativeQuery = true)
    int updatePointByStudentIdx(Long studentIdx, Long points);
}
