package hana.reward.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "QuizRepository", description = "퀴즈 레포지토리")
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {}
