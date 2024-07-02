package hana.story.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoryCommentPaginationDto {
    private Long commentIdx;
    private String commentContent;
    private LocalDateTime createdAt;

    public StoryCommentPaginationDto(
            Long commentIdx, String commentContent, LocalDateTime createdAt) {
        this.commentIdx = commentIdx;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }
}
