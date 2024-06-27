package hana.story.service;

import hana.story.domain.StoryLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class StoryLikeService {
    private final StoryLikeRepository storyLikeRepository;

    public StoryLikeService(StoryLikeRepository storyLikeRepository) {
        this.storyLikeRepository = storyLikeRepository;
    }
}
