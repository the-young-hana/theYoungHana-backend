package hana.member.controller;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.member.dto.StudentQrReadResDto;
import hana.member.dto.StudentReadResDto;
import hana.member.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "StudentController", description = "학생 컨트롤러")
@RestController
@Tag(name = "Student", description = "학생")
@RequestMapping("api/v1")
public class StudentController {
    private final StudentService studentService;

    @MethodInfo(name = "readStudent", description = "학생증을 조회합니다.")
    @GetMapping("/students")
    @Operation(
            summary = "학생증 조회",
            description = "학생증을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "학생증 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = StudentReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "학생증 조회 실패",
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
    public ResponseEntity<StudentReadResDto> readStudent() {
        return null;
    }

    @MethodInfo(name = "readStudentQr", description = "학생증 QR을 조회합니다.")
    @GetMapping("/students/qr")
    @Operation(
            summary = "학생증 QR 조회",
            description = "학생증 QR을 조회합니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "학생증 QR 조회 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                StudentQrReadResDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "학생증 QR 조회 실패",
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
    public ResponseEntity<StudentQrReadResDto> readStudentQr() {
        return null;
    }

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
}
