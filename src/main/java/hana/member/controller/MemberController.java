package hana.member.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.dto.notification.FcmSendReqDto;
import hana.common.exception.BaseExceptionResponse;
import hana.common.service.FCMService;
import hana.common.vo.JwtToken;
import hana.member.domain.MemberToken;
import hana.member.domain.Student;
import hana.member.dto.MemberLoginReqDto;
import hana.member.dto.MemberLoginResDto;
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
                        .token(memberLoginReqDto.getFcmToken())
                        .title("더영하나")
                        .body(
                                student.getStudentName()
                                        + "님, 환영합니다. "
                                        + student.getDept().getDeptName()
                                        + "에서 진행 중인 이벤트를 확인하세요.")
                        .build());

        return ResponseEntity.ok(
                MemberLoginResDto.builder()
                        .data(
                                MemberLoginResDto.Data.builder()
                                        .accessToken(jwtToken.getAccessToken())
                                        .refreshToken(jwtToken.getRefreshToken())
                                        .deptIdx(student.getDept().getDeptIdx())
                                        .build())
                        .build());
    }

    public MemberController(
            MemberService memberService,
            StudentService studentService,
            MemberTokenService memberTokenService,
            FCMService fcmService) {
        this.memberService = memberService;
        this.studentService = studentService;
        this.memberTokenService = memberTokenService;
        this.fcmService = fcmService;
    }
}
