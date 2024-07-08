package hana.story.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryUpdateCommentReqDto {
    private Long commentIdx;
    private String commentContent;

    @Builder
    public StoryUpdateCommentReqDto(Long commentIdx, String commentContent) {
        this.commentIdx = commentIdx;
        this.commentContent = commentContent;
    }
}
