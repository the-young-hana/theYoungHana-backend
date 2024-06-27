package hana.story.service;

import hana.story.domain.StoryComment;
import hana.story.domain.StoryCommentRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoryCommentService {
    private final StoryCommentRepository storyCommentRepository;

    public Long getStoryCommentNum(Long storyIdx) {
        return storyCommentRepository.countByStory_StoryIdx(storyIdx);
    }

    public String getStoryComment(Long storyIdx) {
        Optional<StoryComment> storyComment =
                storyCommentRepository.findFirstByStory_StoryIdx(storyIdx);
        if (storyComment.isPresent()) {
            return storyComment.get().getStoryCommentContent();
        }
        return null;
    }

    public StoryCommentService(StoryCommentRepository storyCommentRepository) {
        this.storyCommentRepository = storyCommentRepository;
    }
}
