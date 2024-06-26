package hana.knowledge.service;

import hana.common.annotation.TypeInfo;
import hana.knowledge.domain.KnowledgeRepository;
import hana.knowledge.dto.KnowledgesReadResDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
                                                .knowledgeSummary(knowledge.getKnowledgeContent())
                                                .knowledgeImage(knowledge.getKnowledgeImage())
                                                .build())
                        .collect(Collectors.toCollection(ArrayList::new));

        return new KnowledgesReadResDto(data);
    }

    public KnowledgeService(KnowledgeRepository knowledgeRepository) {
        this.knowledgeRepository = knowledgeRepository;
    }
}
