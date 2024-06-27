package hana.story.service;

import hana.story.domain.StoryComment;
import hana.story.domain.StoryCommentRepository;
import hana.story.dto.StoryRepresentativeCommentResDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoryCommentService {
    private final StoryCommentRepository storyCommentRepository;

    public Long getStoryCommentNum(Long storyIdx) {
        return storyCommentRepository.countByStory_StoryIdx(storyIdx);
    }

    public StoryRepresentativeCommentResDto getStoryComment(Long storyIdx) {
        Optional<StoryComment> storyComment =
                storyCommentRepository.findFirstByStory_StoryIdx(storyIdx);
        if (storyComment.isPresent()) {
            StoryComment sc = storyComment.get();
            return StoryRepresentativeCommentResDto.builder()
                    .commentIdx(sc.getStoryCommentIdx())
                    .commentContent(sc.getStoryCommentContent())
                    .createdAt(sc.getCreatedAt())
                    .build();
        }
        return null;
    }

    public StoryCommentService(StoryCommentRepository storyCommentRepository) {
        this.storyCommentRepository = storyCommentRepository;
    }
}
