package hana.story.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "StoryComment", description = "스토리 댓글 엔티티")
@Entity
@Table(name = "story_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_comment_idx", nullable = false)
    private Long storyCommentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_idx", nullable = false)
    private Story story;

    @Column(name = "story_comment_content", nullable = false)
    private String storyCommentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_comment_parent_idx")
    private StoryComment storyCommentParent;
}
