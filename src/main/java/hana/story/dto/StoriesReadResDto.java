package hana.story.dto;

import hana.account.dto.DeptAccountTransactionResDto;
import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoriesReadResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public StoriesReadResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long storyIdx;
        private final String storyTitle;
        private final Long storyLikeNum;
        private final Long storyCommentNum;
        private final List<DeptAccountTransactionResDto> transactionList;
        private final LocalDateTime createdAt;
        private final Boolean isLiked;

        @Builder
        public Data(
                Long storyIdx,
                String storyTitle,
                Long storyLikeNum,
                Long storyCommentNum,
                Boolean isLiked,
                List<DeptAccountTransactionResDto> transactionList,
                LocalDateTime createdAt) {
            super();
            this.storyIdx = storyIdx;
            this.storyTitle = storyTitle;
            this.storyLikeNum = storyLikeNum;
            this.isLiked = isLiked;
            this.storyCommentNum = storyCommentNum;
            this.transactionList = transactionList;
            this.createdAt = createdAt;
        }
    }
}
