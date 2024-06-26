package hana.knowledge.service;

import hana.common.annotation.TypeInfo;
import hana.knowledge.domain.Knowledge;
import hana.knowledge.domain.KnowledgeRepository;
import hana.knowledge.dto.KnowledgeReadResDto;
import hana.knowledge.dto.KnowledgesReadResDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hana.knowledge.exception.UnlistedKnowledgeException;
import org.springframework.stereotype.Service;

@TypeInfo(name = "KnowledgeService", description = "금융상식 서비스")
@Service
public class KnowledgeService {
    private final KnowledgeRepository knowledgeRepository;

    public KnowledgesReadResDto getKnowledges() {
        List<KnowledgesReadResDto.Data> data =
                knowledgeRepository.findAll().stream()
                        .map(
                                knowledge ->
                                        KnowledgesReadResDto.Data.builder()
                                                .knowledgeIdx(knowledge.getKnowledgeIdx())
                                                .knowledgeTitle(knowledge.getKnowledgeTitle())
                                                .knowledgeSummary(knowledge.getKnowledgeSummary())
                                                .knowledgeImage(knowledge.getKnowledgeImage())
                                                .build())
                        .collect(Collectors.toCollection(ArrayList::new));

        return new KnowledgesReadResDto(data);
    }

    public KnowledgeReadResDto getKnowledgeById(Long knowledgeIdx) {
        Knowledge knowledge = knowledgeRepository.findById(knowledgeIdx)
                .orElseThrow(UnlistedKnowledgeException::new);

        KnowledgeReadResDto.Data data = KnowledgeReadResDto.Data.builder()
                .knowledgeTitle(knowledge.getKnowledgeTitle())
                .knowledgeContent(knowledge.getKnowledgeContent())
                .knowledgeImage(knowledge.getKnowledgeImage())
                .knowledgeCategory(knowledge.getKnowledgeCategory())
                .createdAt(knowledge.getCreatedAt())
                .updatedAt(knowledge.getUpdatedAt())
                .build();

        return KnowledgeReadResDto.builder()
                .data(data)
                .build();
    }

    public KnowledgeService(KnowledgeRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }
}
