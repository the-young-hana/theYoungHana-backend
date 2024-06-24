package hana.story.dto;

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
        private final Long storyCommentNum;
        private final List<String> storyImageList;
        private final List<Transaction> transactionList;

        @Builder
        public Data(
                Long storyIdx,
                String storyTitle,
                Long storyLikeNum,
                Long storyCommentNum,
                List<String> storyImageList,
                List<Transaction> transactionList) {
            this.storyIdx = storyIdx;
            this.storyTitle = storyTitle;
            this.storyLikeNum = storyLikeNum;
            this.storyCommentNum = storyCommentNum;
            this.storyImageList = storyImageList;
            this.transactionList = transactionList;
        }

        @Getter
        public static class Transaction {
            private final Long transactionIdx;
            private final String transactionId;
            private final String transactionName;
            private final Long transactionAmount;
            private final Long transactionBalance;
            private final String transactionType;
            private final Boolean transactionIsUsed;
            private final LocalDateTime transactionDate;

            @Builder
            public Transaction(
                    Long transactionIdx,
                    String transactionId,
                    String transactionName,
                    Long transactionAmount,
                    Long transactionBalance,
                    String transactionType,
                    Boolean transactionIsUsed,
                    LocalDateTime transactionDate) {
                this.transactionIdx = transactionIdx;
                this.transactionId = transactionId;
                this.transactionName = transactionName;
                this.transactionAmount = transactionAmount;
                this.transactionBalance = transactionBalance;
                this.transactionType = transactionType;
                this.transactionIsUsed = transactionIsUsed;
                this.transactionDate = transactionDate;
            }
        }
    }
}
