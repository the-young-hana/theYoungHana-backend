package hana.story.dto;

import hana.account.dto.TransactionsByDateResDto;
import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoryReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StoryReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long storyIdx;
        private final String storyTitle;
        private final Long storyLikeNum;
        private final String storyContent;
        private final Long storyCommentNum;
        private final Boolean isLiked;
        private final StoryRepresentativeCommentResDto storyComment;
        private final List<String> storyImageList;
        private final List<TransactionsByDateResDto> transactionList;
        private final LocalDateTime createdAt;

        @Builder
        public Data(
                Long storyIdx,
                String storyTitle,
                Long storyLikeNum,
                Long storyCommentNum,
                String storyContent,
                Boolean isLiked,
                List<String> storyImageList,
                StoryRepresentativeCommentResDto storyComment,
                List<TransactionsByDateResDto> transactionList,
                LocalDateTime createdAt) {
            this.storyIdx = storyIdx;
            this.storyTitle = storyTitle;
            this.storyContent = storyContent;
            this.storyLikeNum = storyLikeNum;
            this.isLiked = isLiked;
            this.storyCommentNum = storyCommentNum;
            this.storyImageList = storyImageList;
            this.storyComment = storyComment;
            this.transactionList = transactionList;
            this.createdAt = createdAt;
        }
    }
}
