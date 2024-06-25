package hana.knowledge.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "KnowledgeRepository", description = "금융상식 레포지토리")
@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {}
