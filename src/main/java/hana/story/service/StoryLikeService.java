package hana.story.service;

import hana.story.domain.StoryLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class StoryLikeService {
    private final StoryLikeRepository storyLikeRepository;

    public Long getStoryLikeNum(Long storyIdx) {
        return storyLikeRepository.countByStory_StoryIdx(storyIdx);
    }

    public StoryLikeService(StoryLikeRepository storyLikeRepository) {
        this.storyLikeRepository = storyLikeRepository;
    }
}
