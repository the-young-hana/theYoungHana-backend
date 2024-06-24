package hana.reward.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RewardAnswerQuizResDto extends BaseResponse {
    private final Data data;

    @Builder
    public RewardAnswerQuizResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Boolean isCorrect;
        private final String explanation;

        @Builder
        public Data(Boolean isCorrect, String explanation) {
            this.isCorrect = isCorrect;
            this.explanation = explanation;
        }
    }
}
