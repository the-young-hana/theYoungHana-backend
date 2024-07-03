package hana.college.domain;

import hana.common.annotation.TypeInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "DeptRepository", description = "학과 레포지토리")
@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {
    @Modifying
    @Transactional
    @Query(
            value =
                    "UPDATE departments SET dept_point = dept_point + :points WHERE dept_idx = :deptIdx",
            nativeQuery = true)
    int updatePointByDeptIdx(Long deptIdx, Long points);

    @Query(
            value = "SELECT dept_point FROM departments WHERE dept_idx = :deptIdx",
            nativeQuery = true)
    Long findDeptPointByDeptIdx(Long deptIdx);

    @Query(
            value =
                    "SELECT CAST(@rank := @rank + 1 AS SIGNED) AS ranking, dept_idx, dept_name, dept_point FROM departments, (SELECT @rank := 0) r ORDER BY dept_point DESC LIMIT 10 OFFSET :offset",
            nativeQuery = true)
    List<Object[]> getRanking(int offset);

    Optional<Dept> findByDeptAccountNumber(String deptAccountNumber);

    @Query("select distinct d from Dept d inner join fetch d.account where d.deptIdx = :deptIdx")
    Optional<Dept> findDeptByDeptIdx(Long deptIdx);
}
