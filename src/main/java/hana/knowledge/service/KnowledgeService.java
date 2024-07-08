package hana.knowledge.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hana.common.annotation.TypeInfo;
import hana.knowledge.domain.Knowledge;
import hana.knowledge.domain.KnowledgeRepository;
import hana.knowledge.domain.QKnowledge;
import hana.knowledge.dto.KnowledgePaginationDto;
import hana.knowledge.dto.KnowledgeReadResDto;
import hana.knowledge.dto.KnowledgesReadResDto;
import hana.knowledge.exception.UnlistedKnowledgeException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@TypeInfo(name = "KnowledgeService", description = "금융상식 서비스")
@Service
public class KnowledgeService {
    private final KnowledgeRepository knowledgeRepository;
    private final JPAQueryFactory queryFactory;

    public KnowledgesReadResDto readKnowledges(Long lastKnowledgeIdx) {
        List<KnowledgePaginationDto> knowledgesPage = paginationNoOffset(lastKnowledgeIdx, 10);

        List<KnowledgesReadResDto.Data> knowledgeDataList =
                knowledgesPage.stream()
                        .map(
                                knowledge ->
                                        KnowledgesReadResDto.Data.builder()
                                                .knowledgeIdx(knowledge.getKnowledgeIdx())
                                                .knowledgeTitle(knowledge.getKnowledgeTitle())
                                                .knowledgeSummary(knowledge.getKnowledgeSummary())
                                                .knowledgeImage(knowledge.getKnowledgeImage())
                                                .build())
                        .collect(Collectors.toCollection(ArrayList::new));

        return KnowledgesReadResDto.builder().data(knowledgeDataList).build();
    }

    private List<KnowledgePaginationDto> paginationNoOffset(Long lastKnowledgeIdx, int pageSize) {
        QKnowledge knowledge = QKnowledge.knowledge;
        return queryFactory
                .select(
                        Projections.constructor(
                                KnowledgePaginationDto.class,
                                knowledge.knowledgeIdx,
                                knowledge.knowledgeTitle,
                                knowledge.knowledgeSummary,
                                knowledge.knowledgeImage))
                .from(knowledge)
                .where(gtKnowledgeId(lastKnowledgeIdx), knowledge.deletedYn.isFalse())
                .orderBy(knowledge.knowledgeIdx.asc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanExpression gtKnowledgeId(Long knowledgeId) {
        if (knowledgeId == null) {
            return null;
        }
        return QKnowledge.knowledge.knowledgeIdx.gt(knowledgeId);
    }

    public KnowledgeReadResDto getKnowledgeById(Long knowledgeIdx) {
        Knowledge knowledge =
                knowledgeRepository
                        .findById(knowledgeIdx)
                        .orElseThrow(UnlistedKnowledgeException::new);

        KnowledgeReadResDto.Data data =
                KnowledgeReadResDto.Data.builder()
                        .knowledgeTitle(knowledge.getKnowledgeTitle())
                        .knowledgeContent(knowledge.getKnowledgeContent())
                        .knowledgeImage(knowledge.getKnowledgeImage())
                        .knowledgeCategory(knowledge.getKnowledgeCategory())
                        .createdAt(knowledge.getCreatedAt())
                        .updatedAt(knowledge.getUpdatedAt())
                        .build();

        return KnowledgeReadResDto.builder().data(data).build();
    }

    public KnowledgeService(KnowledgeRepository knowledgeRepository, JPAQueryFactory queryFactory) {
        this.knowledgeRepository = knowledgeRepository;
        this.queryFactory = queryFactory;
    }
}
