package hana.member.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StudentCardRepository", description = "학생증 레포지토리")
@Repository
public interface StudentCardRepository extends JpaRepository<StudentCard, Long> {}
