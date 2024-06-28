package hana.story.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseTimeEntity;
import hana.member.domain.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "StoryLike", description = "스토리 좋아요 엔티티")
@Entity
@Table(name = "story_likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoryLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_like_idx")
    private Long storyLikeIdx;

    @ManyToOne
    @JoinColumn(name = "story_idx")
    private Story story;

    @ManyToOne
    @JoinColumn(name = "student_idx")
    private Student student;
}
