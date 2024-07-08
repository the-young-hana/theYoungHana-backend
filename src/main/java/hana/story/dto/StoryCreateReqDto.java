package hana.story.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryCreateReqDto {
    private String storyTitle;
    private String storyContent;
    private Long deptIdx;
    private List<Long> transactionList;

    @Builder
    public StoryCreateReqDto(
            String storyTitle, String storyContent, Long deptIdx, List<Long> transactionList) {
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.deptIdx = deptIdx;
        this.transactionList = transactionList;
    }
}
