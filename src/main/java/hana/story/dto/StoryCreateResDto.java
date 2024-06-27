package hana.story.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoryCreateResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StoryCreateResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private Long storyIdx;
        private String storyTitle;
        private String storyContent;
        private String storyImageList;

        @Builder
        public Data(Long storyIdx, String storyTitle, String storyContent, String storyImageList) {
            this.storyIdx = storyIdx;
            this.storyTitle = storyTitle;
            this.storyContent = storyContent;
            this.storyImageList = storyImageList;
        }
    }
}
