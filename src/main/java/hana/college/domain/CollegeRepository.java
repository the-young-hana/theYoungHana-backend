package hana.college.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "CollegeRepository", description = "대학 레포지토리")
@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {}
