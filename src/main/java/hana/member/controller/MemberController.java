package hana.member.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.member.dto.MemberLoginReqDto;
import hana.member.dto.MemberLoginResDto;
import hana.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "MemberController", description = "회원 컨트롤러")
@RestController
@Tag(name = "Member", description = "회원")
@RequestMapping("api/v1")
public class MemberController {
    private final MemberService memberService;

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
            @Valid @RequestBody MemberLoginReqDto memberLoginReqDto) {
        return ResponseEntity.ok().body(memberService.login(memberLoginReqDto));
    }

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
