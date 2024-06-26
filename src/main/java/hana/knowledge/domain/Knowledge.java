package hana.knowledge.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Knowledge", description = "금융상식 엔티티")
@Entity
@Table(name = "knowledges")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Knowledge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "knowledge_idx", nullable = false)
    private Long knowledgeIdx;

    @Column(name = "knowledge_title", nullable = false, length = 50)
    private String knowledgeTitle;

    @Column(name = "knowledge_content", nullable = false, columnDefinition = "LONGTEXT")
    private String knowledgeContent;

    @Column(name = "knowledge_summary", nullable = false, columnDefinition = "LONGTEXT")
    private String knowledgeSummary;

    @Column(name = "knowledge_image", nullable = false)
    private String knowledgeImage;

    @Column(name = "knowledge_category", nullable = false)
    private String knowledgeCategory;
}
