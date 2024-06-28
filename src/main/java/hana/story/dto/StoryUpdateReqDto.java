package hana.story.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoryUpdateReqDto {
    private String storyTitle;
    private String storyContent;
    private List<Long> transactionList;

    @Builder
    public StoryUpdateReqDto(String storyTitle, String storyContent, List<Long> transactionList) {
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.transactionList = transactionList;
    }
}
