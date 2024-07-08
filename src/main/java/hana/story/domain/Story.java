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
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "story_like_amount", columnDefinition = "bigint default 0")
    @ColumnDefault("0")
    private Long storyLikeAmount;

    @Column(name = "story_comment_amount", columnDefinition = "bigint default 0")
    @ColumnDefault("0")
    private Long storyCommentAmount;

    @Builder
    public Story(String storyTitle, String storyContent, Dept dept) {
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyLikeAmount = 0L;
        this.storyCommentAmount = 0L;
        this.dept = dept;
    }

    public void postImages(String storyImageList) {
        this.storyImageList = storyImageList;
    }

    public void update(StoryUpdateReqDto storyUpdateReqDto) {
        this.storyTitle = storyUpdateReqDto.getStoryTitle();
        this.storyContent = storyUpdateReqDto.getStoryContent();
    }

    public void delete() {
        this.deletedYn = true;
    }

    public void decrementLikeAmount() {
        this.storyLikeAmount = this.storyLikeAmount - 1;
    }

    public void incrementLikeAmount() {
        this.storyLikeAmount = this.storyLikeAmount + 1;
    }

    public void decrementCommentAmount() {
        this.storyCommentAmount = this.storyCommentAmount - 1;
    }

    public void incrementCommentAmount() {
        this.storyCommentAmount = this.storyCommentAmount + 1;
    }
}
