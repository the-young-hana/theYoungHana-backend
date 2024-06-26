package hana.member.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StudentRepository", description = "학생 레포지토리")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByMember(Member member);

    Student findByMember_MemberIdx(Long memberIdx);
}
