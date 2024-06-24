package hana.knowledge.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KnowledgeReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public KnowledgeReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String knowledgeTitle;
        private final String knowledgeContent;
        private final String knowledgeImage;
        private final String knowledgeCategory;
        private final LocalDateTime createdAt;
        private final LocalDateTime updatedAt;

        @Builder
        public Data(
                String knowledgeTitle,
                String knowledgeContent,
                String knowledgeImage,
                String knowledgeCategory,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
            this.knowledgeTitle = knowledgeTitle;
            this.knowledgeContent = knowledgeContent;
            this.knowledgeImage = knowledgeImage;
            this.knowledgeCategory = knowledgeCategory;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
