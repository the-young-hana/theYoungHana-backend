package hana.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hana.common.annotation.AuthenticatedMember;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.common.exception.PageStartIndexException;
import hana.common.utils.ImageUtils;
import hana.common.utils.JsonUtils;
import hana.common.utils.JwtUtils;
import hana.event.domain.*;
import hana.event.dto.*;
import hana.event.exception.*;
import hana.event.exception.EventDeleteOnlyAdminException;
import hana.event.service.EventService;
import hana.member.domain.Member;
import hana.member.domain.Student;
import hana.member.service.MemberTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@TypeInfo(name = "EventController", description = "이벤트 컨트롤러")
@RestController
@Tag(name = "Event", description = "이벤트")
@RequestMapping("api/v1")
public class EventController {
    private final EventService eventService;
    private final MemberTokenService memberTokenService;
    private final JwtUtils jwtUtils;
    private final ImageUtils imageUtils;
    private final JsonUtils jsonUtils;

    @MethodInfo(name = "readEvents", description = "이벤트 목록을 조회합니다.")
    @GetMapping("/events")
    @AuthenticatedMember
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
            @RequestParam(value = "value", required = false) String value,
            @RequestParam("isEnd") Boolean isEnd,
            @RequestParam("page") Integer page) {
        if (page == 0) {
            throw new PageStartIndexException();
        }

        return ResponseEntity.ok(
                EventsReadResDto.builder()
                        .data(
                                eventService
                                        .searchEvents(
                                                value,
                                                isEnd,
                                                page - 1,
                                                jwtUtils.getStudent().getDept().getDeptIdx())
                                        .stream()
                                        .map(
                                                event ->
                                                        EventsReadResDto.Data.builder()
                                                                .eventIdx(event.getEventIdx())
                                                                .eventTitle(event.getEventTitle())
                                                                .eventSummary(
                                                                        event.getEventContent()
                                                                                                .length()
                                                                                        > 30
                                                                                ? event.getEventContent()
                                                                                        .substring(
                                                                                                0,
                                                                                                30)
                                                                                        .concat(
                                                                                                "...")
                                                                                : event
                                                                                        .getEventContent())
                                                                .eventType(
                                                                        event.getEventType()
                                                                                .getDescription())
                                                                .eventStart(
                                                                        event
                                                                                .getEventStartDatetime())
                                                                .eventEnd(
                                                                        event.getEventEndDatetime())
                                                                .eventCreateAt(event.getCreatedAt())
                                                                .build())
                                        .toList())
                        .build());
    }

    @MethodInfo(name = "readEvent", description = "이벤트를 상세 조회합니다.")
    @GetMapping("/events/{eventIdx}")
    @AuthenticatedMember
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
    public ResponseEntity<EventReadResDto> readEvent(@PathVariable("eventIdx") Long eventIdx)
            throws JsonProcessingException {
        Member member = jwtUtils.getMember();
        Event event = eventService.readEvent(eventIdx);

        if (event == null) {
            throw new UnavailableEventException();
        }

        if (!Objects.equals(
                event.getDept().getDeptIdx(), jwtUtils.getStudent().getDept().getDeptIdx())) {
            throw new DeptNotMatchException();
        }

        return ResponseEntity.ok(
                EventReadResDto.builder()
                        .data(
                                EventReadResDto.Data.builder()
                                        .eventIdx(event.getEventIdx())
                                        .isEnd(
                                                LocalDateTime.now()
                                                                .isBefore(
                                                                        event
                                                                                .getEventStartDatetime())
                                                        ? 0
                                                        : LocalDateTime.now()
                                                                        .isAfter(
                                                                                event
                                                                                        .getEventEndDatetime())
                                                                ? 2
                                                                : 1)
                                        .isMine(
                                                event.getCreatedBy()
                                                        .getMemberIdx()
                                                        .equals(member.getMemberIdx()))
                                        .eventType(event.getEventType())
                                        .eventTitle(event.getEventTitle())
                                        .eventStart(event.getEventStartDatetime())
                                        .eventEnd(event.getEventEndDatetime())
                                        .eventDt(event.getEventDatetime())
                                        .eventFee(event.getEventFee())
                                        .eventFeeStart(event.getEventFeeStartDatetime())
                                        .eventFeeEnd(event.getEventFeeEndDatetime())
                                        .eventContent(event.getEventContent())
                                        .eventCreateAt(event.getCreatedAt())
                                        .eventImageList(
                                                jsonUtils.convertJsonToList(
                                                        event.getEventImageList()))
                                        .eventLimit(event.getEventLimit())
                                        .eventPrizeList(
                                                eventService
                                                        .readEventPrizes(event.getEventIdx())
                                                        .stream()
                                                        .map(
                                                                eventPrize ->
                                                                        EventReadResDto.Data
                                                                                .EventPrize
                                                                                .builder()
                                                                                .prizeRank(
                                                                                        eventPrize
                                                                                                .getEventPrizeRank())
                                                                                .prizeName(
                                                                                        eventPrize
                                                                                                .getEventPrizeName())
                                                                                .prizeLimit(
                                                                                        eventPrize
                                                                                                .getEventPrizeLimit())
                                                                                .build())
                                                        .toList())
                                        .build())
                        .build());
    }

    @MethodInfo(name = "createEvent", description = "이벤트를 추가합니다.")
    @PostMapping(value = "/events", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AuthenticatedMember
    @Operation(
            summary = "이벤트 추가",
            description = "이벤트를 추가합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 추가 성공",
                        content =
                                @Content(schema = @Schema(implementation = EventReadResDto.class))),
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
    public ResponseEntity<EventReadResDto> createEvent(
            @Valid @RequestPart("eventCreateReqDto") EventCreateReqDto eventCreateReqDto,
            @RequestPart(value = "eventImageList", required = false)
                    List<MultipartFile> eventImageList)
            throws JsonProcessingException {

        if (!jwtUtils.getStudent().getStudentIsAdmin()) {
            throw new EventCreateOnlyAdminException();
        }

        LocalDateTime eventStart =
                LocalDateTime.parse(
                        eventCreateReqDto.getEventStart(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventEnd =
                LocalDateTime.parse(
                        eventCreateReqDto.getEventEnd(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventDt =
                LocalDateTime.parse(
                        eventCreateReqDto.getEventDt(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventFeeStart =
                LocalDateTime.parse(
                        eventCreateReqDto.getEventFeeStart(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventFeeEnd =
                LocalDateTime.parse(
                        eventCreateReqDto.getEventFeeEnd(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (eventStart.isBefore(LocalDateTime.now())) {
            throw new EventStartInvalidException();
        }

        if (eventEnd.isBefore(eventStart)) {
            throw new EventEndInvalidException();
        }

        if (eventDt.isBefore(eventEnd)) {
            throw new EventDateInvalidException();
        }

        if (eventFeeStart.isBefore(LocalDateTime.now())) {
            throw new EventFeeStartInvalidException();
        }

        if (eventFeeEnd.isBefore(eventFeeStart)) {
            throw new EventFeeEndInvalidException();
        }

        Event event =
                eventService.createEvent(
                        Event.builder()
                                .eventTitle(eventCreateReqDto.getEventTitle())
                                .eventContent(eventCreateReqDto.getEventContent())
                                .eventStartDatetime(eventStart)
                                .eventEndDatetime(eventEnd)
                                .eventDatetime(eventDt)
                                .eventFee(eventCreateReqDto.getEventFee())
                                .eventFeeStartDatetime(eventFeeStart)
                                .eventFeeEndDatetime(eventFeeEnd)
                                .eventLimit(eventCreateReqDto.getEventLimit())
                                .eventType(EventEnumType.valueOf(eventCreateReqDto.getEventType()))
                                .dept(jwtUtils.getStudent().getDept())
                                .eventImageList("[]")
                                .build());

        Event updateEvent =
                eventService.updateEvent(
                        event.getEventIdx(),
                        Event.builder()
                                .eventTitle(event.getEventTitle())
                                .eventContent(event.getEventContent())
                                .eventStartDatetime(event.getEventStartDatetime())
                                .eventEndDatetime(event.getEventEndDatetime())
                                .eventDatetime(event.getEventDatetime())
                                .eventFee(event.getEventFee())
                                .eventFeeStartDatetime(event.getEventFeeStartDatetime())
                                .eventFeeEndDatetime(event.getEventFeeEndDatetime())
                                .eventLimit(event.getEventLimit())
                                .eventType(event.getEventType())
                                .dept(event.getDept())
                                .eventImageList(
                                        eventImageList != null
                                                ? jsonUtils.convertListToJson(
                                                        imageUtils.createImages(
                                                                "events/" + event.getEventIdx(),
                                                                eventImageList))
                                                : "[]")
                                .build());

        List<EventPrize> eventPrizes =
                eventCreateReqDto.getEventPrizeList().stream()
                        .map(
                                eventPrize ->
                                        EventPrize.builder()
                                                .eventPrizeRank(eventPrize.getPrizeRank())
                                                .eventPrizeName(eventPrize.getPrizeName())
                                                .eventPrizeLimit(eventPrize.getPrizeLimit())
                                                .event(updateEvent)
                                                .build())
                        .toList();

        eventService.createEventPrizes(eventPrizes);
        createEventSchedule(updateEvent);

        return ResponseEntity.ok(
                EventReadResDto.builder()
                        .data(
                                EventReadResDto.Data.builder()
                                        .eventIdx(updateEvent.getEventIdx())
                                        .isEnd(
                                                LocalDateTime.now()
                                                                .isBefore(
                                                                        updateEvent
                                                                                .getEventStartDatetime())
                                                        ? 0
                                                        : LocalDateTime.now()
                                                                        .isAfter(
                                                                                updateEvent
                                                                                        .getEventEndDatetime())
                                                                ? 2
                                                                : 1)
                                        .isMine(true)
                                        .eventType(updateEvent.getEventType())
                                        .eventTitle(updateEvent.getEventTitle())
                                        .eventStart(updateEvent.getEventStartDatetime())
                                        .eventEnd(updateEvent.getEventEndDatetime())
                                        .eventDt(updateEvent.getEventDatetime())
                                        .eventFee(updateEvent.getEventFee())
                                        .eventFeeStart(updateEvent.getEventFeeStartDatetime())
                                        .eventFeeEnd(updateEvent.getEventFeeEndDatetime())
                                        .eventContent(updateEvent.getEventContent())
                                        .eventCreateAt(event.getCreatedAt())
                                        .eventImageList(
                                                jsonUtils.convertJsonToList(
                                                        updateEvent.getEventImageList()))
                                        .eventLimit(
                                                updateEvent.getEventType().equals(EventEnumType.응모)
                                                        ? eventPrizes.stream()
                                                                .mapToLong(
                                                                        EventPrize
                                                                                ::getEventPrizeLimit)
                                                                .sum()
                                                        : updateEvent.getEventLimit())
                                        .eventPrizeList(
                                                eventPrizes.stream()
                                                        .map(
                                                                eventPrize ->
                                                                        EventReadResDto.Data
                                                                                .EventPrize
                                                                                .builder()
                                                                                .prizeRank(
                                                                                        eventPrize
                                                                                                .getEventPrizeRank())
                                                                                .prizeName(
                                                                                        eventPrize
                                                                                                .getEventPrizeName())
                                                                                .prizeLimit(
                                                                                        eventPrize
                                                                                                .getEventPrizeLimit())
                                                                                .build())
                                                        .toList())
                                        .build())
                        .build());
    }

    @MethodInfo(name = "updateEvent", description = "이벤트를 수정합니다.")
    @PutMapping(value = "/events/{eventIdx}")
    @AuthenticatedMember
    @Operation(
            summary = "이벤트 수정",
            description = "이벤트를 수정합니다.",
            method = "PUT",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 수정 성공",
                        content =
                                @Content(schema = @Schema(implementation = EventReadResDto.class))),
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
    public ResponseEntity<EventReadResDto> updateEvent(
            @PathVariable("eventIdx") Long eventIdx,
            @RequestBody EventUpdateReqDto eventUpdateReqDto)
            throws JsonProcessingException {
        if (LocalDateTime.now().isAfter(eventService.readEvent(eventIdx).getEventStartDatetime())) {
            throw new InProgressEventException();
        }

        LocalDateTime eventStart =
                LocalDateTime.parse(
                        eventUpdateReqDto.getEventStart(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventEnd =
                LocalDateTime.parse(
                        eventUpdateReqDto.getEventEnd(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventDt =
                LocalDateTime.parse(
                        eventUpdateReqDto.getEventDt(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventFeeStart =
                LocalDateTime.parse(
                        eventUpdateReqDto.getEventFeeStart(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eventFeeEnd =
                LocalDateTime.parse(
                        eventUpdateReqDto.getEventFeeEnd(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (eventStart.isBefore(LocalDateTime.now())) {
            throw new EventStartInvalidException();
        }

        if (eventEnd.isBefore(eventStart)) {
            throw new EventEndInvalidException();
        }

        if (eventDt.isBefore(eventEnd)) {
            throw new EventDateInvalidException();
        }

        if (eventFeeStart.isBefore(LocalDateTime.now())) {
            throw new EventFeeStartInvalidException();
        }

        if (eventFeeEnd.isBefore(eventFeeStart)) {
            throw new EventFeeEndInvalidException();
        }

        Student student = jwtUtils.getStudent();

        if (!student.getStudentIsAdmin()
                || !(student.getDept()
                        .getDeptIdx()
                        .equals(eventService.readEvent(eventIdx).getDept().getDeptIdx()))) {
            throw new EventUpdateOnlyAdminException();
        }

        Event currentEvent = eventService.readEvent(eventIdx);
        Event updateEvent =
                eventService.updateEvent(
                        eventIdx,
                        Event.builder()
                                .eventTitle(eventUpdateReqDto.getEventTitle())
                                .eventContent(eventUpdateReqDto.getEventContent())
                                .eventStartDatetime(eventStart)
                                .eventEndDatetime(eventEnd)
                                .eventDatetime(eventDt)
                                .eventFee(eventUpdateReqDto.getEventFee())
                                .eventFeeStartDatetime(eventFeeStart)
                                .eventFeeEndDatetime(eventFeeEnd)
                                .eventLimit(eventUpdateReqDto.getEventLimit())
                                .eventType(EventEnumType.valueOf(eventUpdateReqDto.getEventType()))
                                .dept(currentEvent.getDept())
                                .eventImageList(currentEvent.getEventImageList())
                                .build());

        List<EventPrize> eventPrizes =
                eventUpdateReqDto.getEventPrizeList().stream()
                        .map(
                                eventPrize ->
                                        EventPrize.builder()
                                                .eventPrizeRank(eventPrize.getPrizeRank())
                                                .eventPrizeName(eventPrize.getPrizeName())
                                                .eventPrizeLimit(eventPrize.getPrizeLimit())
                                                .event(updateEvent)
                                                .build())
                        .toList();

        eventService.updateEventPrizes(eventIdx, eventPrizes);

        eventService.deleteScheduledEvent(eventIdx);
        createEventSchedule(updateEvent);

        return ResponseEntity.ok(
                EventReadResDto.builder()
                        .data(
                                EventReadResDto.Data.builder()
                                        .eventIdx(updateEvent.getEventIdx())
                                        .isEnd(
                                                LocalDateTime.now()
                                                                .isBefore(
                                                                        updateEvent
                                                                                .getEventStartDatetime())
                                                        ? 0
                                                        : LocalDateTime.now()
                                                                        .isAfter(
                                                                                updateEvent
                                                                                        .getEventEndDatetime())
                                                                ? 2
                                                                : 1)
                                        .isMine(true)
                                        .eventType(updateEvent.getEventType())
                                        .eventTitle(updateEvent.getEventTitle())
                                        .eventStart(updateEvent.getEventStartDatetime())
                                        .eventEnd(updateEvent.getEventEndDatetime())
                                        .eventDt(updateEvent.getEventDatetime())
                                        .eventFee(updateEvent.getEventFee())
                                        .eventFeeStart(updateEvent.getEventFeeStartDatetime())
                                        .eventFeeEnd(updateEvent.getEventFeeEndDatetime())
                                        .eventContent(updateEvent.getEventContent())
                                        .eventCreateAt(updateEvent.getCreatedAt())
                                        .eventImageList(
                                                jsonUtils.convertJsonToList(
                                                        updateEvent.getEventImageList()))
                                        .eventLimit(
                                                updateEvent.getEventType().equals(EventEnumType.응모)
                                                        ? eventPrizes.stream()
                                                                .mapToLong(
                                                                        EventPrize
                                                                                ::getEventPrizeLimit)
                                                                .sum()
                                                        : updateEvent.getEventLimit())
                                        .eventPrizeList(
                                                eventPrizes.stream()
                                                        .map(
                                                                eventPrize ->
                                                                        EventReadResDto.Data
                                                                                .EventPrize
                                                                                .builder()
                                                                                .prizeRank(
                                                                                        eventPrize
                                                                                                .getEventPrizeRank())
                                                                                .prizeName(
                                                                                        eventPrize
                                                                                                .getEventPrizeName())
                                                                                .prizeLimit(
                                                                                        eventPrize
                                                                                                .getEventPrizeLimit())
                                                                                .build())
                                                        .toList())
                                        .build())
                        .build());
    }

    @MethodInfo(name = "deleteEvent", description = "이벤트를 삭제합니다.")
    @DeleteMapping("/events/{eventIdx}")
    @AuthenticatedMember
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
        if (LocalDateTime.now().isAfter(eventService.readEvent(eventIdx).getEventStartDatetime())) {
            throw new InProgressEventException();
        }
        Student student = jwtUtils.getStudent();

        if (!student.getStudentIsAdmin()
                || !(student.getDept()
                        .getDeptIdx()
                        .equals(eventService.readEvent(eventIdx).getDept().getDeptIdx()))) {
            throw new EventDeleteOnlyAdminException();
        }

        eventService.deleteEvent(eventIdx);
        eventService.deleteEventPrizes(eventIdx);
        eventService.deleteScheduledEvent(eventIdx);

        return ResponseEntity.ok(EventDeleteResDto.builder().build());
    }

    @MethodInfo(name = "readEventWinners", description = "이벤트 당첨자 목록을 조회합니다.")
    @GetMapping("/events/{eventIdx}/winners")
    @AuthenticatedMember
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
        List<EventPrize> eventPrizes = eventService.readEventPrizes(eventIdx);
        List<EventWinner> eventWinners = eventService.readEventWinners(eventIdx);

        return ResponseEntity.ok(
                EventReadWinnersResDto.builder()
                        .data(
                                eventPrizes.stream()
                                        .map(
                                                eventPrize ->
                                                        EventReadWinnersResDto.Data.builder()
                                                                .prizeRank(
                                                                        eventPrize
                                                                                .getEventPrizeRank())
                                                                .prizeName(
                                                                        eventPrize
                                                                                .getEventPrizeName())
                                                                .winnerList(
                                                                        eventWinners.stream()
                                                                                .filter(
                                                                                        eventWinner ->
                                                                                                eventWinner
                                                                                                        .getEventPrize()
                                                                                                        .getEventPrizeIdx()
                                                                                                        .equals(
                                                                                                                eventPrize
                                                                                                                        .getEventPrizeIdx()))
                                                                                .map(
                                                                                        eventWinner ->
                                                                                                EventReadWinnersResDto
                                                                                                        .Data
                                                                                                        .Winner
                                                                                                        .builder()
                                                                                                        .memberId(
                                                                                                                eventWinner
                                                                                                                        .getStudent()
                                                                                                                        .getMember()
                                                                                                                        .getMemberId())
                                                                                                        .memberName(
                                                                                                                eventWinner
                                                                                                                        .getStudent()
                                                                                                                        .getMember()
                                                                                                                        .getMemberName())
                                                                                                        .build())
                                                                                .toList())
                                                                .build())
                                        .toList())
                        .build());
    }

    @MethodInfo(name = "setEventOn", description = "이벤트 알림을 활성화합니다.")
    @PostMapping("/events/{eventIdx}/push")
    @AuthenticatedMember
    @Operation(
            summary = "이벤트 알림 활성화",
            description = "이벤트 알림을 활성화합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "이벤트 알림 활성화 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = EventPushOnResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "이벤트 알림 활성화 실패",
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
    public ResponseEntity<EventPushOnResDto> setEventOn(@PathVariable("eventIdx") Long eventIdx) {
        eventService.updateEventPush(
                eventIdx, memberTokenService.findByMemberIdx(jwtUtils.getMember().getMemberIdx()));
        return ResponseEntity.ok(EventPushOnResDto.builder().build());
    }

    @MethodInfo(name = "joinEvent", description = "이벤트에 참여합니다.")
    @PostMapping("/events/{eventIdx}/join")
    @AuthenticatedMember
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
        Student student = jwtUtils.getStudent();
        Event event = eventService.readEvent(eventIdx);

        if (LocalDateTime.now().isBefore(event.getEventStartDatetime())) {
            throw new NotStartEventException();
        }

        if (LocalDateTime.now().isAfter(event.getEventEndDatetime())) {
            throw new AlreadyEndEventException();
        }

        switch (event.getEventType().getDescription()) {
            case "신청" -> {
                if (eventService.isEventWinner(eventIdx, student.getStudentIdx())) {
                    throw new EventAlreadyApplyException();
                }
                eventService.createEventWinner(
                        EventWinner.builder()
                                .eventPrize(eventService.readEventPrize(eventIdx))
                                .student(student)
                                .build());
                return ResponseEntity.ok(
                        EventJoinResDto.builder()
                                .data(
                                        EventJoinResDto.Data.builder()
                                                .eventCount(0L)
                                                .eventLimit(0L)
                                                .build())
                                .build());
            }
            case "응모" -> {
                if (eventService.isEventArrival(eventIdx, student.getStudentIdx())) {
                    throw new EventAlreadyApplyException();
                }
                eventService.createEventArrival(
                        EventArrival.builder()
                                .isWinner(false)
                                .event(event)
                                .student(student)
                                .build());
                return ResponseEntity.ok(
                        EventJoinResDto.builder()
                                .data(
                                        EventJoinResDto.Data.builder()
                                                .eventCount(
                                                        eventService.countEventArrivals(eventIdx))
                                                .eventLimit(event.getEventLimit())
                                                .build())
                                .build());
            }
            case "선착" -> {
                // 이벤트 상품 찾기
                EventPrize eventPrize = eventService.readEventPrize(eventIdx);

                // 이벤트 참여자 수 찾기
                Long eventWinnerCount = eventService.countEventWinners(eventIdx);

                // 만약, 이벤트 참여자 수가 이벤트 참여 가능 인원보다 많다면
                if (eventWinnerCount >= event.getEventLimit()) {
                    throw new RuntimeException("이벤트 참여 가능 인원을 초과하였습니다.");
                }

                // 이벤트 참여자 수가 이벤트 참여 가능 인원보다 적다면, 당첨
                eventService.createEventWinner(
                        EventWinner.builder().eventPrize(eventPrize).student(student).build());
                return ResponseEntity.ok(EventJoinResDto.builder().build());
            }
            default -> throw new RuntimeException("이벤트 타입이 잘못되었습니다.");
        }
    }

    @MethodInfo(name = "createEventSchedule", description = "이벤트 일정을 생성합니다.")
    private void createEventSchedule(Event updateEvent) {
        eventService.scheduleEvent(
                ScheduledEvent.builder()
                        .eventIdx(updateEvent.getEventIdx())
                        .eventTitle(updateEvent.getEventTitle())
                        .eventStartDatetime(updateEvent.getEventStartDatetime())
                        .eventEndDatetime(updateEvent.getEventEndDatetime())
                        .eventDatetime(updateEvent.getEventDatetime())
                        .eventFee(updateEvent.getEventFee())
                        .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                        .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                        .eventLimit(updateEvent.getEventLimit())
                        .deptIdx(updateEvent.getDept().getDeptIdx())
                        .scheduledEventType(ScheduledEventEnumType.신청_시작)
                        .scheduledDatetime(
                                updateEvent
                                        .getEventStartDatetime()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .ttl(
                                updateEvent.getEventStartDatetime().toEpochSecond(ZoneOffset.UTC)
                                        - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                        + 60)
                        .build());

        eventService.scheduleEvent(
                ScheduledEvent.builder()
                        .eventIdx(updateEvent.getEventIdx())
                        .eventTitle(updateEvent.getEventTitle())
                        .eventStartDatetime(updateEvent.getEventStartDatetime())
                        .eventEndDatetime(updateEvent.getEventEndDatetime())
                        .eventDatetime(updateEvent.getEventDatetime())
                        .eventFee(updateEvent.getEventFee())
                        .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                        .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                        .eventLimit(updateEvent.getEventLimit())
                        .deptIdx(updateEvent.getDept().getDeptIdx())
                        .scheduledEventType(ScheduledEventEnumType.신청_마감)
                        .scheduledDatetime(
                                updateEvent
                                        .getEventEndDatetime()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .ttl(
                                updateEvent.getEventEndDatetime().toEpochSecond(ZoneOffset.UTC)
                                        - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                        + 60)
                        .build());

        if (updateEvent.getEventType().getDescription() == "응모") {
            eventService.scheduleEvent(
                    ScheduledEvent.builder()
                            .eventIdx(updateEvent.getEventIdx())
                            .eventTitle(updateEvent.getEventTitle())
                            .eventStartDatetime(updateEvent.getEventStartDatetime())
                            .eventEndDatetime(updateEvent.getEventEndDatetime())
                            .eventDatetime(updateEvent.getEventDatetime())
                            .eventFee(updateEvent.getEventFee())
                            .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                            .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                            .eventLimit(updateEvent.getEventLimit())
                            .deptIdx(updateEvent.getDept().getDeptIdx())
                            .scheduledEventType(ScheduledEventEnumType.응모_시작)
                            .scheduledDatetime(
                                    updateEvent
                                            .getEventDatetime()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd HH:mm")))
                            .ttl(
                                    updateEvent.getEventDatetime().toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                            + 60)
                            .build());
        }

        if (updateEvent.getEventType().getDescription() == "선착") {
            eventService.scheduleEvent(
                    ScheduledEvent.builder()
                            .eventIdx(updateEvent.getEventIdx())
                            .eventTitle(updateEvent.getEventTitle())
                            .eventStartDatetime(updateEvent.getEventStartDatetime())
                            .eventEndDatetime(updateEvent.getEventEndDatetime())
                            .eventDatetime(updateEvent.getEventDatetime())
                            .eventFee(updateEvent.getEventFee())
                            .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                            .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                            .eventLimit(updateEvent.getEventLimit())
                            .deptIdx(updateEvent.getDept().getDeptIdx())
                            .scheduledEventType(ScheduledEventEnumType.선착_시작)
                            .scheduledDatetime(
                                    updateEvent
                                            .getEventStartDatetime()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd HH:mm")))
                            .ttl(
                                    updateEvent
                                                    .getEventStartDatetime()
                                                    .toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                            + 60)
                            .build());

            eventService.scheduleEvent(
                    ScheduledEvent.builder()
                            .eventIdx(updateEvent.getEventIdx())
                            .eventTitle(updateEvent.getEventTitle())
                            .eventStartDatetime(updateEvent.getEventStartDatetime())
                            .eventEndDatetime(updateEvent.getEventEndDatetime())
                            .eventDatetime(updateEvent.getEventDatetime())
                            .eventFee(updateEvent.getEventFee())
                            .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                            .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                            .eventLimit(updateEvent.getEventLimit())
                            .deptIdx(updateEvent.getDept().getDeptIdx())
                            .scheduledEventType(ScheduledEventEnumType.선착_마감)
                            .scheduledDatetime(
                                    updateEvent
                                            .getEventDatetime()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd HH:mm")))
                            .ttl(
                                    updateEvent.getEventDatetime().toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                            + 60)
                            .build());
        }

        if (updateEvent.getEventFee() != 0) {
            eventService.scheduleEvent(
                    ScheduledEvent.builder()
                            .eventIdx(updateEvent.getEventIdx())
                            .eventTitle(updateEvent.getEventTitle())
                            .eventStartDatetime(updateEvent.getEventStartDatetime())
                            .eventEndDatetime(updateEvent.getEventEndDatetime())
                            .eventDatetime(updateEvent.getEventDatetime())
                            .eventFee(updateEvent.getEventFee())
                            .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                            .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                            .eventLimit(updateEvent.getEventLimit())
                            .deptIdx(updateEvent.getDept().getDeptIdx())
                            .scheduledEventType(ScheduledEventEnumType.입금_시작)
                            .scheduledDatetime(
                                    updateEvent
                                            .getEventFeeStartDatetime()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd HH:mm")))
                            .ttl(
                                    updateEvent
                                                    .getEventFeeStartDatetime()
                                                    .toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                            + 60)
                            .build());

            eventService.scheduleEvent(
                    ScheduledEvent.builder()
                            .eventIdx(updateEvent.getEventIdx())
                            .eventTitle(updateEvent.getEventTitle())
                            .eventStartDatetime(updateEvent.getEventStartDatetime())
                            .eventEndDatetime(updateEvent.getEventEndDatetime())
                            .eventDatetime(updateEvent.getEventDatetime())
                            .eventFee(updateEvent.getEventFee())
                            .eventFeeStartDatetime(updateEvent.getEventFeeStartDatetime())
                            .eventFeeEndDatetime(updateEvent.getEventFeeEndDatetime())
                            .eventLimit(updateEvent.getEventLimit())
                            .deptIdx(updateEvent.getDept().getDeptIdx())
                            .scheduledEventType(ScheduledEventEnumType.입금_마감)
                            .scheduledDatetime(
                                    updateEvent
                                            .getEventFeeEndDatetime()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd HH:mm")))
                            .ttl(
                                    updateEvent
                                                    .getEventFeeEndDatetime()
                                                    .toEpochSecond(ZoneOffset.UTC)
                                            - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
                                            + 60)
                            .build());
        }
    }

    public EventController(
            EventService eventService,
            MemberTokenService memberTokenService,
            JwtUtils jwtUtils,
            ImageUtils imageUtils,
            JsonUtils jsonUtils) {
        this.eventService = eventService;
        this.memberTokenService = memberTokenService;
        this.jwtUtils = jwtUtils;
        this.imageUtils = imageUtils;
        this.jsonUtils = jsonUtils;
    }
}
