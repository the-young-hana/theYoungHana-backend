package hana.story.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.common.utils.JwtUtils;
import hana.story.dto.*;
import hana.story.service.StoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@TypeInfo(name = "StoryController", description = "스토리 컨트롤러")
@RestController
@Tag(name = "Story", description = "스토리")
@RequestMapping("api/v1")
public class StoryController {
    private final StoryService storyService;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "readStories", description = "스토리 목록을 조회합니다.")
    @GetMapping("/stories/{deptIdx}")
    @Operation(
            summary = "스토리 목록 조회",
            description = "스토리 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = StoriesReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 목록 조회 실패",
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
    public ResponseEntity<StoriesReadResDto> readStories(
            @RequestParam("page") Integer page, @PathVariable("deptIdx") Long deptIdx) {

        return ResponseEntity.ok(storyService.getStories(page, deptIdx));
    }

    @MethodInfo(name = "readStory", description = "스토리를 상세 조회합니다.")
    @GetMapping("/stories/{storyIdx}/detail")
    @Operation(
            summary = "스토리 상세 조회",
            description = "스토리를 상세 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 조회 성공",
                        content =
                                @Content(schema = @Schema(implementation = StoryReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 조회 실패",
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
    public ResponseEntity<StoryReadResDto> readStory(@PathVariable("storyIdx") Long storyIdx) {

        return ResponseEntity.ok(storyService.getStory(storyIdx));
    }

    @PostMapping(
            value = "/stories",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "스토리 추가",
            description = "스토리를 추가합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 추가 성공",
                        content =
                                @Content(schema = @Schema(implementation = StoryReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 추가 실패",
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
    public ResponseEntity<StoryReadResDto> createStory(
            @RequestPart(value = "StoryCreateReqDto") StoryCreateReqDto storyCreateReqDto,
            @RequestPart(value = "imgs", required = false) List<MultipartFile> imgs) {
        return ResponseEntity.ok(storyService.createStory(storyCreateReqDto, imgs));
    }

    @MethodInfo(name = "updateStory", description = "스토리를 수정합니다.")
    @PutMapping(
            value = "/stories/{storyIdx}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "스토리 수정",
            description = "스토리를 수정합니다.",
            method = "PUT",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 수정 성공",
                        content =
                                @Content(schema = @Schema(implementation = StoryReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 수정 실패",
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
    public ResponseEntity<StoryReadResDto> updateStory(
            @PathVariable("storyIdx") Long storyIdx,
            @RequestPart(value = "StoryUpdateReqDto") StoryUpdateReqDto storyUpdateReqDto,
            @RequestPart(value = "imgs", required = false) List<MultipartFile> imgs) {
        return ResponseEntity.ok(storyService.updateStory(storyIdx, storyUpdateReqDto, imgs));
    }

    @MethodInfo(name = "deleteStory", description = "스토리를 삭제합니다.")
    @DeleteMapping("/stories/{storyIdx}")
    @Operation(
            summary = "스토리 삭제",
            description = "스토리를 삭제합니다.",
            method = "DELETE",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 삭제 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = StoryDeleteResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 삭제 실패",
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
    public ResponseEntity<StoryDeleteResDto> deleteStory(@PathVariable("storyIdx") Long storyIdx) {
        return ResponseEntity.ok(storyService.deleteStory(storyIdx));
    }

    @MethodInfo(name = "createStoryLike", description = "스토리 좋아요를 추가합니다.")
    @PostMapping("/stories/{storyIdx}/likes")
    @Operation(
            summary = "스토리 좋아요 추가",
            description = "스토리 좋아요를 추가합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "스토리 좋아요 추가 성공",
                        content =
                                @Content(schema = @Schema(implementation = StoryReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "스토리 좋아요 추가 실패",
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
    public ResponseEntity<StoryReadResDto> createStoryLike(
            @PathVariable("storyIdx") Long storyIdx) {
        return ResponseEntity.ok(storyService.toggleStoryLike(storyIdx, jwtUtils.getStudent()));
    }

    public StoryController(StoryService storyService, JwtUtils jwtUtils) {
        this.storyService = storyService;
        this.jwtUtils = jwtUtils;
    }
}
