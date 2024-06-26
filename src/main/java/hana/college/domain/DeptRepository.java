package hana.college.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "DeptRepository", description = "학과 레포지토리")
@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE departments SET dept_point = dept_point + 5 WHERE dept_idx = :deptIdx",
            nativeQuery = true)
    int updatePointByDeptIdx(@Param("deptIdx") Long deptIdx);
}
