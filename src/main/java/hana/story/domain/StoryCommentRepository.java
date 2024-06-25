package hana.story.domain;

import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StoryCommentRepository", description = "스토리 댓글 레포지토리")
@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComment, Long> {}
