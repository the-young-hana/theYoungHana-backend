package hana.event.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.event.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "EventController", description = "이벤트 컨트롤러")
@RestController
@Tag(name = "Event", description = "이벤트")
@RequestMapping("api/v1")
public class EventController {
    @MethodInfo(name = "readEvents", description = "이벤트 목록을 조회합니다.")
    @GetMapping("/events")
    @Operation(
            summary = "이벤트 목록 조회",
            description = "이벤트 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 목록 조회 성공",
                        content =
                                @Content(
                                        schema = @Schema(implementation = EventsReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 목록 조회 실패",
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
    public ResponseEntity<EventsReadResDto> readEvents(
            @RequestParam("value") String value,
            @RequestParam("isEnd") Boolean isEnd,
            @RequestParam("page") Long page) {
        return null;
    }

    @MethodInfo(name = "readEvent", description = "이벤트를 상세 조회합니다.")
    @GetMapping("/events/{eventIdx}")
    @Operation(
            summary = "이벤트 상세 조회",
            description = "이벤트를 상세 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 상세 조회 성공",
                        content =
                                @Content(schema = @Schema(implementation = EventReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 상세 조회 실패",
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
    public ResponseEntity<EventReadResDto> readEvent(@PathVariable("eventIdx") Long eventIdx) {
        return null;
    }

    @MethodInfo(name = "createEvent", description = "이벤트를 추가합니다.")
    @PostMapping("/events")
    @Operation(
            summary = "이벤트 추가",
            description = "이벤트를 추가합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "이벤트 추가 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    EventCreateReqDto.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 추가 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = EventCreateResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 추가 실패",
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
    public ResponseEntity<EventCreateResDto> createEvent(
            @Valid @RequestBody EventCreateReqDto eventCreateReqDto) {
        return null;
    }

    @MethodInfo(name = "updateEvent", description = "이벤트를 수정합니다.")
    @PutMapping("/events/{eventIdx}")
    @Operation(
            summary = "이벤트 수정",
            description = "이벤트를 수정합니다.",
            method = "PUT",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "이벤트 수정 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    EventUpdateReqDto.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 수정 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = EventUpdateResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 수정 실패",
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
    public ResponseEntity<EventUpdateResDto> updateEvent(
            @PathVariable("eventIdx") Long eventIdx, EventUpdateReqDto eventUpdateReqDto) {
        return null;
    }

    @MethodInfo(name = "deleteEvent", description = "이벤트를 삭제합니다.")
    @DeleteMapping("/events/{eventIdx}")
    @Operation(
            summary = "이벤트 삭제",
            description = "이벤트를 삭제합니다.",
            method = "DELETE",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 삭제 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = EventDeleteResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 삭제 실패",
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
    public ResponseEntity<EventDeleteResDto> deleteEvent(@PathVariable("eventIdx") Long eventIdx) {
        return null;
    }

    @MethodInfo(name = "readEventWinners", description = "이벤트 당첨자 목록을 조회합니다.")
    @GetMapping("/events/{eventIdx}/winners")
    @Operation(
            summary = "이벤트 당첨자 목록 조회",
            description = "이벤트 당첨자 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 당첨자 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                EventReadWinnersResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 당첨자 목록 조회 실패",
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
    public ResponseEntity<EventReadWinnersResDto> readEventWinners(
            @PathVariable("eventIdx") Long eventIdx) {
        return null;
    }

    @MethodInfo(name = "joinEvent", description = "이벤트에 참여합니다.")
    @PostMapping("/events/{eventIdx}/join")
    @Operation(
            summary = "이벤트 참여",
            description = "이벤트에 참여합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 참여 성공",
                        content =
                                @Content(schema = @Schema(implementation = EventJoinResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 참여 실패",
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
    public ResponseEntity<EventJoinResDto> joinEvent(@PathVariable("eventIdx") Long eventIdx) {
        return null;
    }
}
