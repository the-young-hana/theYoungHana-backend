package hana.story.domain;

import hana.common.annotation.TypeInfo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StoryCommentRepository", description = "스토리 댓글 레포지토리")
@Repository
public interface StoryCommentRepository extends JpaRepository<StoryComment, Long> {

    Long countByStory_StoryIdxAndDeletedYnFalse(Long storyIdx);

    Optional<StoryComment> findFirstByStory_StoryIdxAndDeletedYnFalse(Long storyIdx);

    List<StoryComment> findAllByStory_StoryIdxAndStoryCommentParentIsNullAndDeletedYnFalse(
            Long storyIdx, Pageable pageable);

    List<StoryComment> findAllByStoryCommentParent_StoryCommentIdx(Long storyCommentParentIdx);
}
