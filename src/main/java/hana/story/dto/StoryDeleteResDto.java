package hana.story.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoryDeleteResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StoryDeleteResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String storyInfo;

        @Builder
        public Data(Long storyIdx) {
            this.storyInfo = storyIdx + "번 스토리 삭제 완료";
        }
    }
}
