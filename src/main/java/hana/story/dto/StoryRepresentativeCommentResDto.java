package hana.story.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoryRepresentativeCommentResDto {
    private final Long commentIdx;
    private final String commentContent;
    private final LocalDateTime createdAt;

    @Builder
    public StoryRepresentativeCommentResDto(
            Long commentIdx, String commentContent, LocalDateTime createdAt) {
        this.commentIdx = commentIdx;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }
}
