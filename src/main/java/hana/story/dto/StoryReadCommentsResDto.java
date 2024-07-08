package hana.story.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoryReadCommentsResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public StoryReadCommentsResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long commentIdx;
        private final String commentContent;
        private final List<Reply> replyList;
        private final String studentNickname;
        private final LocalDateTime createdAt;
        private final Long createdBy;

        @Builder
        public Data(
                Long commentIdx,
                String commentContent,
                List<Reply> replyList,
                String studentNickname,
                LocalDateTime createdAt,
                Long createdBy) {
            this.commentIdx = commentIdx;
            this.commentContent = commentContent;
            this.replyList = replyList;
            this.studentNickname = studentNickname;
            this.createdAt = createdAt;
            this.createdBy = createdBy;
        }

        @Getter
        public static class Reply {
            private final Long commentIdx;
            private final Long commentParentIdx;
            private final String commentContent;
            private final String studentNickname;
            private final LocalDateTime createdAt;
            private final Long createdBy;

            @Builder
            public Reply(
                    Long commentIdx,
                    Long commentParentIdx,
                    String commentContent,
                    String studentNickname,
                    LocalDateTime createdAt,
                    Long createdBy) {
                this.commentIdx = commentIdx;
                this.commentParentIdx = commentParentIdx;
                this.commentContent = commentContent;
                this.studentNickname = studentNickname;
                this.createdAt = createdAt;
                this.createdBy = createdBy;
            }
        }
    }
}
