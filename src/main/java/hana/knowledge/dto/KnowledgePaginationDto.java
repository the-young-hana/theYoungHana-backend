package hana.knowledge.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KnowledgePaginationDto {
    private Long knowledgeIdx;
    private String knowledgeTitle;
    private String knowledgeSummary;
    private String knowledgeImage;

    public KnowledgePaginationDto(
            Long knowledgeIdx,
            String knowledgeTitle,
            String knowledgeSummary,
            String knowledgeImage) {
        this.knowledgeIdx = knowledgeIdx;
        this.knowledgeTitle = knowledgeTitle;
        this.knowledgeSummary = knowledgeSummary;
        this.knowledgeImage = knowledgeImage;
    }
}
