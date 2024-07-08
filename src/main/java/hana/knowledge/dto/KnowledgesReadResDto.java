package hana.knowledge.dto;

import hana.common.dto.BaseResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KnowledgesReadResDto extends BaseResponse {
    private final List<Data> data;

    @Builder
    public KnowledgesReadResDto(List<Data> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long knowledgeIdx;
        private final String knowledgeTitle;
        private final String knowledgeSummary;
        private final String knowledgeImage;

        @Builder
        public Data(
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
}
