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
    private Long createdBy;
    private boolean deletedYn;

    public StoryCommentPaginationDto(
            Long commentIdx,
            String commentContent,
            LocalDateTime createdAt,
            Long createdBy,
            boolean deletedYn) {
        this.commentIdx = commentIdx;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.deletedYn = deletedYn;
    }
}
