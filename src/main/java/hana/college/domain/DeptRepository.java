package hana.college.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "DeptRepository", description = "학과 레포지토리")
@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {}
