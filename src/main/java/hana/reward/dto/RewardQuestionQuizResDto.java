package hana.reward.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RewardQuestionQuizResDto extends BaseResponse {
    private final Data data;

    @Builder
    public RewardQuestionQuizResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long quizIdx;
        private final String quizContent;

        @Builder
        public Data(Long quizIdx, String quizContent) {
            this.quizIdx = quizIdx;
            this.quizContent = quizContent;
        }
    }
}
