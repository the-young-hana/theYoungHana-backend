package hana.story.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @Column(name = "story_image_list", nullable = false, columnDefinition = "JSON")
    private String storyImageList;
}
