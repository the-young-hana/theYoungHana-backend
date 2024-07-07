package hana.account.controller;

import hana.account.dto.AccountPwCheckReqDto;
import hana.account.dto.AccountPwCheckResDto;
import hana.account.dto.AccountReadResDto;
import hana.account.service.AccountService;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.common.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "AccountController", description = "계좌 컨트롤러")
@RestController
@Tag(name = "Account", description = "계좌")
@RequestMapping("api/v1")
public class AccountController {
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "readAccountInfo", description = "내 계좌 목록을 조회합니다.")
    @GetMapping("/accounts")
    @Operation(
            summary = "내 계좌 목록 조회",
            description = "내 계좌 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "계좌 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = AccountReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "계좌 목록 조회 실패",
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
    public ResponseEntity<AccountReadResDto> readAccounts() {
        return ResponseEntity.ok(accountService.getMyAccounts(jwtUtils.getMember().getMemberIdx()));
    }

    @MethodInfo(name = "readDeptInfo", description = "계좌 비밀번호를 확인합니다.")
    @PostMapping("/accounts")
    @Operation(
            summary = "계좌 비밀번호 확인",
            description = "계좌 비밀번호를 확인합니다.",
            method = "POST",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "비밀번호 확인",
                            required = true,
                            content =
                                    @Content(
                                            schema =
                                                    @Schema(
                                                            implementation =
                                                                    AccountPwCheckReqDto.class))),
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "계좌 비밀번호 확인 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                AccountPwCheckResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "계좌 비밀번호 확인 실패",
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
    public ResponseEntity<AccountPwCheckResDto> checkAccountPw(
            @RequestBody AccountPwCheckReqDto accountPwCheckReqDto) {
        return ResponseEntity.ok(
                accountService.checkAccountPw(jwtUtils.getMember(), accountPwCheckReqDto));
    }

    public AccountController(AccountService accountService, JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
    }
}
