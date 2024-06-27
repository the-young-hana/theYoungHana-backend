package hana.story.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StoryRepository", description = "스토리 레포지토리")
@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Page<Story> findByDept_DeptIdx(Long deptIdx, Pageable pageable);
}
