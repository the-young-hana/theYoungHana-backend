package hana.reward.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.common.utils.JwtUtils;
import hana.reward.dto.*;
import hana.reward.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "RewardController", description = "리워드 컨트롤러")
@RestController
@Tag(name = "Reward", description = "리워드")
@RequestMapping("api/v1")
public class RewardController {
    private final RewardService rewardService;
    private final JwtUtils jwtUtils;

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
        Long studentIdx = jwtUtils.getStudent().getStudentIdx();
        Long deptIdx = jwtUtils.getStudent().getDept().getDeptIdx();
        RewardReadResDto getMyContribution = rewardService.getMyContribution(studentIdx, deptIdx);
        return ResponseEntity.ok(getMyContribution);
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
    public ResponseEntity<RewardReadDeptRankResDto> readDeptRewards(@RequestParam int page) {
        return ResponseEntity.ok(rewardService.getDeptRank(page));
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
        Long studentIdx = jwtUtils.getStudent().getStudentIdx();
        RewardPresentResDto getPoints = rewardService.getPoints(studentIdx);
        return ResponseEntity.ok(getPoints);
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
        Long studentIdx = jwtUtils.getStudent().getStudentIdx();
        RewardQuestionQuizResDto getRandomQuiz = rewardService.getRandomQuiz(studentIdx);
        return ResponseEntity.ok(getRandomQuiz);
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
        Long studentIdx = jwtUtils.getStudent().getStudentIdx();
        RewardAnswerQuizResDto isCollect =
                rewardService.isCollect(studentIdx, rewardAnswerQuizReqDto);
        return ResponseEntity.ok(isCollect);
    }

    @Builder
    public RewardController(RewardService rewardService, JwtUtils jwtUtils) {
        this.rewardService = rewardService;
        this.jwtUtils = jwtUtils;
    }
}
