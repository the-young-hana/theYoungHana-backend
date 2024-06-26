package hana.knowledge.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.knowledge.dto.KnowledgeReadResDto;
import hana.knowledge.dto.KnowledgesReadResDto;
import hana.knowledge.service.KnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "KnowledgeController", description = "금융상식 컨트롤러")
@RestController
@Tag(name = "Knowledge", description = "금융상식")
@RequestMapping("api/v1")
public class KnowledgeController {
    private final KnowledgeService knowledgeService;

    @MethodInfo(name = "readKnowledges", description = "금융상식 목록을 조회합니다.")
    @GetMapping("/knowledges")
    @Operation(
            summary = "금융상식 목록 조회",
            description = "금융상식 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "금융상식 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                KnowledgesReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "금융상식 목록 조회 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "403",
                        description = "접근 권한 없음",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 오류",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    public ResponseEntity<KnowledgesReadResDto> readKnowledges() {
        KnowledgesReadResDto response = knowledgeService.getKnowledges();
        return ResponseEntity.ok(response);
    }

    @MethodInfo(name = "readKnowledge", description = "금융상식을 상세 조회합니다.")
    @GetMapping("/knowledges/{knowledgeIdx}")
    @Operation(
            summary = "금융상식 상세 조회",
            description = "금융상식을 상세 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "금융상식 상세 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                KnowledgeReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "금융상식 상세 조회 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "403",
                        description = "접근 권한 없음",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 오류",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    public ResponseEntity<KnowledgeReadResDto> readKnowledge(@PathVariable Long knowledgeIdx) {
        return null;
    }

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }
}
