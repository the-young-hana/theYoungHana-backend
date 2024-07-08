package hana.college.controller;

import hana.college.dto.DeptInfoReadResDto;
import hana.college.service.DeptService;
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

@TypeInfo(name = "DeptController", description = "학과 컨트롤러")
@RestController
@Tag(name = "Dept", description = "학과")
@RequestMapping("api/v1")
public class DeptController {
    private final DeptService deptService;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "readDeptInfo", description = "학과 계좌 정보를 조회합니다.")
    @GetMapping("/dept")
    @Operation(
            summary = "학과 계좌 조회",
            description = "학과 계좌 정보를 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "학과 계좌 정보 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                DeptInfoReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "학과 계좌 정보 조회 실패",
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
    public ResponseEntity<DeptInfoReadResDto> readDeptInfo() {
        return ResponseEntity.ok(
                DeptInfoReadResDto.builder()
                        .data(
                                deptService.getDeptAccountInfo(
                                        jwtUtils.getStudent().getDept().getDeptIdx()))
                        .build());
    }

    public DeptController(DeptService deptService, JwtUtils jwtUtils) {
        this.deptService = deptService;
        this.jwtUtils = jwtUtils;
    }
}
