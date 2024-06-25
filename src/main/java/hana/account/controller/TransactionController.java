package hana.account.controller;

import hana.account.dto.TransactionsReadResDto;
import hana.account.service.TransactionService;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@TypeInfo(name = "TransactionController", description = "거래내역 컨트롤러")
@RestController
@Tag(name = "Transaction", description = "거래")
@RequestMapping("api/v1")
public class TransactionController {
    private final TransactionService transactionService;

    @MethodInfo(name = "readTransactions", description = "거래 목록을 조회합니다.")
    @GetMapping("/transactions/{deptIdx}")
    @Operation(
            summary = "거래 목록 조회",
            description = "거래 목록을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "거래 목록 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                TransactionsReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "거래 목록 조회 실패",
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
    public ResponseEntity<TransactionsReadResDto> readTransactions(
            @PathVariable("deptIdx") Long deptIdx,
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("type") String type,
            @RequestParam("sort") String sort,
            @RequestParam("page") Long page) {
        return null;
    }

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
}
