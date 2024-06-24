package hana.story.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryCreateCommentReqDto {
    private Long commentParentIdx;
    private String commentContent;

    @Builder
    public StoryCreateCommentReqDto(Long commentParentIdx, String commentContent) {
        this.commentParentIdx = commentParentIdx;
        this.commentContent = commentContent;
    }
}
