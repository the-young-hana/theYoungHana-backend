package hana.story.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoryUpdateCommentResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StoryUpdateCommentResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long commentIdx;
        private final Long commentParentIdx;
        private final String commentContent;

        @Builder
        public Data(Long commentIdx, Long commentParentIdx, String commentContent) {
            this.commentIdx = commentIdx;
            this.commentParentIdx = commentParentIdx;
            this.commentContent = commentContent;
        }
    }
}
