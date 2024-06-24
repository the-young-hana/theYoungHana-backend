package hana.story.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class StoryCreateReqDto {
    private String storyTitle;
    private String storyContent;
    private List<MultipartFile> storyImageList;
    private List<Long> transactionList;

    @Builder
    public StoryCreateReqDto(
            String storyTitle,
            String storyContent,
            List<MultipartFile> storyImageList,
            List<Long> transactionList) {
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyImageList = storyImageList;
        this.transactionList = transactionList;
    }
}
