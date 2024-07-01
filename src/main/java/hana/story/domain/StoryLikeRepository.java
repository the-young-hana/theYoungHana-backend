package hana.story.domain;

import hana.common.annotation.TypeInfo;
import hana.member.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "StoryLikeRepository", description = "스토리 좋아요 레포지토리")
@Repository
public interface StoryLikeRepository extends JpaRepository<StoryLike, Long> {

    Long countByStory_StoryIdx(Long storyIdx);

    void deleteByStoryAndStudent(Story story, Student student);

    Boolean existsByStoryAndStudent(Story story, Student student);
}
