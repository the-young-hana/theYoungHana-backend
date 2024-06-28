package hana.story.domain;

import hana.college.domain.Dept;
import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import hana.story.dto.StoryUpdateReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Story", description = "스토리 엔티티")
@Entity
@Table(name = "stories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Story extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_idx", nullable = false)
    private Long storyIdx;

    @Column(name = "story_title", nullable = false)
    private String storyTitle;

    @Column(name = "story_content", nullable = false)
    private String storyContent;

    @Column(name = "story_image_list")
    private String storyImageList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_idx")
    private Dept dept;

    @Builder
    public Story(String storyTitle, String storyContent, Dept dept) {
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.dept = dept;
    }

    public void postImages(String storyImageList) {
        this.storyImageList = storyImageList;
    }

    public void update(StoryUpdateReqDto storyUpdateReqDto) {
        this.storyTitle = storyUpdateReqDto.getStoryTitle();
        this.storyContent = storyUpdateReqDto.getStoryContent();
    }
}
