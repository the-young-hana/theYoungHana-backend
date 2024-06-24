package hana.reward.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.reward.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "RewardController", description = "리워드 컨트롤러")
@RestController
@Tag(name = "Reward", description = "리워드")
@RequestMapping("api/v1")
public class RewardController {
    @MethodInfo(name = "readReward", description = "내 리워드를 조회합니다.")
    @GetMapping("/rewards")
    @Operation(
            summary = "내 리워드 조회",
            description = "내 리워드를 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "내 리워드 조회 성공",
                        content =
                                @Content(
                                        schema = @Schema(implementation = RewardReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "내 리워드 조회 실패",
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
    public ResponseEntity<RewardReadResDto> readReward() {
        return null;
    }

    @MethodInfo(name = "readDeptRewards", description = "학과 리워드 랭킹을 조회합니다.")
    @GetMapping("/rewards/rank")
    @Operation(
            summary = "학과 리워드 랭킹 조회",
            description = "학과 리워드 랭킹을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "학과 리워드 랭킹 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                RewardReadDeptRankResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "학과 리워드 랭킹 조회 실패",
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
    public ResponseEntity<RewardReadDeptRankResDto> readDeptRewards() {
        return null;
    }

    @MethodInfo(name = "present", description = "선물을 엽니다.")
    @GetMapping("/rewards/present")
    @Operation(
            summary = "선물 열기",
            description = "선물을 엽니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "선물 열기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                RewardPresentResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "선물 열기 실패",
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
    public ResponseEntity<RewardPresentResDto> present() {
        return null;
    }

    @MethodInfo(name = "questionQuiz", description = "퀴즈를 냅니다.")
    @GetMapping("/rewards/quiz")
    @Operation(
            summary = "퀴즈 내기",
            description = "퀴즈를 냅니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "퀴즈 내기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                RewardQuestionQuizResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "퀴즈 내기 실패",
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
    public ResponseEntity<RewardQuestionQuizResDto> questionQuiz() {
        return null;
    }

    @MethodInfo(name = "answerQuiz", description = "퀴즈를 풉니다.")
    @PostMapping("/rewards/quiz")
    @Operation(
            summary = "퀴즈 풀기",
            description = "퀴즈를 풉니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "퀴즈 풀기 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    RewardAnswerQuizReqDto.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "퀴즈 풀기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                RewardAnswerQuizResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "퀴즈 풀기 실패",
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
    public ResponseEntity<RewardAnswerQuizResDto> answerQuiz(
            @Valid @RequestBody RewardAnswerQuizReqDto rewardAnswerQuizReqDto) {
        return null;
    }
}
