package hana.story.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.story.dto.*;
import hana.story.service.StoryCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "StoryCommentController", description = "스토리 댓글 컨트롤러")
@RestController
@Tag(name = "StoryComment", description = "스토리 댓글")
@RequestMapping("api/v1")
public class StoryCommentController {
    private final StoryCommentService storyCommentService;

    @MethodInfo(name = "readStoryComments", description = "스토리 댓글 목록을 조회합니다.")
    @GetMapping("/stories/{storyIdx}/comments")
    @Operation(
            summary = "스토리 댓글 목록 조회",
            description = "스토리 댓글 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 댓글 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StoryReadCommentsResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 댓글 목록 조회 실패",
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
    public ResponseEntity<StoryReadCommentsResDto> readStoryComments(
            @PathVariable("storyIdx") Long storyIdx,
            @RequestParam(value = "lastCommentIdx", required = false) Long lastCommentIdx) {
        StoryReadCommentsResDto response =
                storyCommentService.readStoryComments(storyIdx, lastCommentIdx);
        return ResponseEntity.ok(response);
    }

    @MethodInfo(name = "createStoryComment", description = "스토리 댓글을 추가합니다.")
    @PostMapping("/stories/{storyIdx}/comments")
    @Operation(
            summary = "스토리 댓글 추가",
            description = "스토리 댓글을 추가합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "스토리 댓글 추가 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    StoryCreateCommentReqDto
                                                                            .class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 댓글 추가 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StoryCreateCommentResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 댓글 추가 실패",
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
    public ResponseEntity<StoryCreateCommentResDto> createStoryComment(
            @PathVariable("storyIdx") Long storyIdx,
            @Valid @RequestBody StoryCreateCommentReqDto storyCreateCommentReqDto) {
        StoryCreateCommentResDto response =
                storyCommentService.createStoryComment(storyIdx, storyCreateCommentReqDto);
        return ResponseEntity.ok(response);
    }

    @MethodInfo(name = "updateStoryComment", description = "스토리 댓글을 수정합니다.")
    @PutMapping("/stories/{storyIdx}/comments/{commentIdx}")
    @Operation(
            summary = "스토리 댓글 수정",
            description = "스토리 댓글을 수정합니다.",
            method = "PUT",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "스토리 댓글 수정 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    StoryUpdateCommentReqDto
                                                                            .class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 댓글 수정 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StoryUpdateCommentResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 댓글 수정 실패",
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
    public ResponseEntity<StoryUpdateCommentResDto> updateStoryComment(
            @PathVariable("storyIdx") Long storyIdx,
            @PathVariable("commentIdx") Long commentIdx,
            @Valid @RequestBody StoryUpdateCommentReqDto storyUpdateCommentReqDto) {
        StoryUpdateCommentResDto response =
                storyCommentService.updateStoryComment(
                        storyIdx, commentIdx, storyUpdateCommentReqDto);
        return ResponseEntity.ok(response);
    }

    @MethodInfo(name = "deleteStoryComment", description = "스토리 댓글을 삭제합니다.")
    @DeleteMapping("/stories/{storyIdx}/comments/{commentIdx}")
    @Operation(
            summary = "스토리 댓글 삭제",
            description = "스토리 댓글을 삭제합니다.",
            method = "DELETE",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 댓글 삭제 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StoryDeleteCommentResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 댓글 삭제 실패",
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
    public ResponseEntity<StoryDeleteCommentResDto> deleteStoryComment(
            @PathVariable("storyIdx") Long storyIdx, @PathVariable("commentIdx") Long commentIdx) {
        storyCommentService.deleteStoryComment(storyIdx, commentIdx);
        StoryDeleteCommentResDto response = StoryDeleteCommentResDto.builder().build();
        return ResponseEntity.ok(response);
    }

    public StoryCommentController(StoryCommentService storyCommentService) {
        this.storyCommentService = storyCommentService;
    }
}
