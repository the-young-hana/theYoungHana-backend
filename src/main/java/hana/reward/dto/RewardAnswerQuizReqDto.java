package hana.reward.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RewardAnswerQuizReqDto {
    private Long quizIdx;
    private Boolean answer;

    @Builder
    public RewardAnswerQuizReqDto(Long quizIdx, Boolean answer) {
        this.quizIdx = quizIdx;
        this.answer = answer;
    }
}
