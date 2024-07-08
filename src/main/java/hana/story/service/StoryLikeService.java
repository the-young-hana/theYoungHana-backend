package hana.story.service;

import hana.member.domain.Student;
import hana.story.domain.Story;
import hana.story.domain.StoryLike;
import hana.story.domain.StoryLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class StoryLikeService {
    private final StoryLikeRepository storyLikeRepository;

    public Boolean checkLike(Story story, Student student) {
        return storyLikeRepository.existsByStoryAndStudent(story, student);
    }

    public void createLike(Story story, Student student) {
        storyLikeRepository.save(StoryLike.builder().story(story).student(student).build());
    }

    public void deleteLike(Story story, Student student) {
        storyLikeRepository.deleteByStoryAndStudent(story, student);
    }

    public StoryLikeService(StoryLikeRepository storyLikeRepository) {
        this.storyLikeRepository = storyLikeRepository;
    }
}
