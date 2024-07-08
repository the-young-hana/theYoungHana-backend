package hana.member.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.dto.notification.FcmSendReqDto;
import hana.common.exception.BaseExceptionResponse;
import hana.common.service.FCMService;
import hana.common.utils.JwtUtils;
import hana.common.vo.JwtToken;
import hana.member.domain.MemberToken;
import hana.member.domain.Notice;
import hana.member.domain.Student;
import hana.member.dto.*;
import hana.member.service.MemberService;
import hana.member.service.MemberTokenService;
import hana.member.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "MemberController", description = "회원 컨트롤러")
@RestController
@Tag(name = "Member", description = "회원")
@RequestMapping("api/v1")
public class MemberController {
    private final MemberService memberService;
    private final StudentService studentService;
    private final MemberTokenService memberTokenService;
    private final FCMService fcmService;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "login", description = "회원 간편 로그인을 실행합니다.")
    @PostMapping("/member/login")
    @Operation(
            summary = "회원 간편 로그인",
            description = "회원 간편 로그인을 실행합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "회원 간편 로그인 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    MemberLoginReqDto.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 간편 로그인 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = MemberLoginResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 간편 로그인 실패",
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
    public ResponseEntity<MemberLoginResDto> login(
            @Valid @RequestBody MemberLoginReqDto memberLoginReqDto) throws IOException {
        JwtToken jwtToken = memberService.login(memberLoginReqDto.getPassword());
        Student student =
                studentService.findStudentByMemberIdx(
                        memberService.findByMemberPw(memberLoginReqDto.getPassword()));

        if (student == null) {
            return ResponseEntity.ok(
                    MemberLoginResDto.builder()
                            .data(
                                    MemberLoginResDto.Data.builder()
                                            .accessToken(jwtToken.getAccessToken())
                                            .refreshToken(jwtToken.getRefreshToken())
                                            .build())
                            .build());
        }

        memberTokenService.save(
                MemberToken.builder()
                        .memberIdx(student.getMember().getMemberIdx())
                        .studentIdx(student.getStudentIdx())
                        .deptIdx(student.getDept().getDeptIdx())
                        .fcmToken(memberLoginReqDto.getFcmToken())
                        .accessToken(jwtToken.getAccessToken())
                        .refreshToken(jwtToken.getRefreshToken())
                        .ttl(60L * 60L * 24L * 60L)
                        .build());

        fcmService.sendMessageTo(
                FcmSendReqDto.builder()
                        .memberIdx(student.getMember().getMemberIdx())
                        .token(memberLoginReqDto.getFcmToken())
                        .title("로그인 성공")
                        .body(
                                student.getStudentName()
                                        + "님, 환영합니다. "
                                        + student.getDept().getDeptName()
                                        + "에서 진행 중인 이벤트를 확인하세요.")
                        .category("더영하나")
                        .build());

        return ResponseEntity.ok(
                MemberLoginResDto.builder()
                        .data(
                                MemberLoginResDto.Data.builder()
                                        .accessToken(jwtToken.getAccessToken())
                                        .refreshToken(jwtToken.getRefreshToken())
                                        .build())
                        .build());
    }

    @MethodInfo(name = "studentLogin", description = "더영하나 로그인을 실행합니다.")
    @PostMapping("/student/login")
    @Operation(
            summary = "더영하나 로그인",
            description = "더영하나 로그인을 실행합니다.",
            method = "POST",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "더영하나 로그인 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StudentLoginResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 간편 로그인 실패",
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
    public ResponseEntity<StudentLoginResDto> theYoungHanaLogin() {
        return ResponseEntity.ok(
                StudentLoginResDto.builder()
                        .data(
                                StudentLoginResDto.Data.builder()
                                        .deptIdx(jwtUtils.getStudent().getDept().getDeptIdx())
                                        .build())
                        .build());
    }

    @MethodInfo(name = "createNotice", description = "회원 알림을 생성합니다.")
    @PostMapping("/member/notice")
    @Operation(
            summary = "회원 알림 생성",
            description = "회원 알림을 생성합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "회원 알림 생성 요청",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    MemberCreateNoticeReqDto
                                                                            .class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 알림 생성 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                MemberCreateNoticeResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 알림 생성 실패",
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
    public ResponseEntity<MemberCreateNoticeResDto> createNotice(
            @Valid @RequestBody MemberCreateNoticeReqDto memberCreateNoticeReqDto)
            throws IOException {
        fcmService.sendMessageTo(
                FcmSendReqDto.builder()
                        .memberIdx(jwtUtils.getMember().getMemberIdx())
                        .token(
                                memberTokenService
                                        .findByMemberIdx(jwtUtils.getMember().getMemberIdx())
                                        .getFcmToken())
                        .title(memberCreateNoticeReqDto.getNoticeTitle())
                        .body(memberCreateNoticeReqDto.getNoticeContent())
                        .category(memberCreateNoticeReqDto.getNoticeCategory())
                        .build());

        return ResponseEntity.ok(MemberCreateNoticeResDto.builder().build());
    }

    @MethodInfo(name = "readNotices", description = "회원 알림 목록을 조회합니다.")
    @GetMapping("/member/notice")
    @Operation(
            summary = "회원 알림 목록 조회",
            description = "회원 알림 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 알림 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                MemberReadNoticesResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 알림 목록 조회 실패",
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
    public ResponseEntity<MemberReadNoticesResDto> readNotices() {
        List<MemberReadNoticesResDto.Notices> noticesList =
                memberService.readNotices(jwtUtils.getMember().getMemberIdx()).stream()
                        .collect(
                                Collectors.groupingBy(
                                        notice -> notice.getCreatedAt().toLocalDate()))
                        .entrySet()
                        .stream()
                        .map(
                                entry ->
                                        MemberReadNoticesResDto.Notices.builder()
                                                .noticeCreatedAt(entry.getKey().toString())
                                                .notices(
                                                        entry.getValue().stream()
                                                                .sorted(
                                                                        Comparator.comparing(
                                                                                        Notice
                                                                                                ::getCreatedAt)
                                                                                .reversed())
                                                                .map(
                                                                        notice ->
                                                                                MemberReadNoticesResDto
                                                                                        .Notices
                                                                                        .Notice
                                                                                        .builder()
                                                                                        .noticeIdx(
                                                                                                notice
                                                                                                        .getNoticeIdx())
                                                                                        .noticeTitle(
                                                                                                notice
                                                                                                        .getNoticeTitle())
                                                                                        .noticeCategory(
                                                                                                notice
                                                                                                        .getNoticeCategory())
                                                                                        .noticeContent(
                                                                                                notice
                                                                                                        .getNoticeContent())
                                                                                        .noticeCreatedAt(
                                                                                                notice
                                                                                                        .getCreatedAt())
                                                                                        .build())
                                                                .collect(Collectors.toList()))
                                                .build())
                        .collect(Collectors.toList());

        return ResponseEntity.ok(MemberReadNoticesResDto.builder().data(noticesList).build());
    }

    @MethodInfo(name = "deleteNotice", description = "회원 알림을 삭제합니다.")
    @DeleteMapping("/member/notice/{noticeIdx}")
    @Operation(
            summary = "회원 알림 삭제",
            description = "회원 알림을 삭제합니다.",
            method = "DELETE",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 알림 삭제 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                MemberDeleteNoticeResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "회원 알림 삭제 실패",
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
    public ResponseEntity<MemberDeleteNoticeResDto> deleteNotice(@PathVariable Long noticeIdx) {
        memberService.deleteNotice(noticeIdx);
        return ResponseEntity.ok(MemberDeleteNoticeResDto.builder().build());
    }

    public MemberController(
            MemberService memberService,
            StudentService studentService,
            MemberTokenService memberTokenService,
            FCMService fcmService,
            JwtUtils jwtUtils) {
        this.memberService = memberService;
        this.studentService = studentService;
        this.memberTokenService = memberTokenService;
        this.fcmService = fcmService;
        this.jwtUtils = jwtUtils;
    }
}
