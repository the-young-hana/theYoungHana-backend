package hana.member.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import hana.common.annotation.AuthenticatedMember;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.exception.BaseExceptionResponse;
import hana.common.utils.JwtUtils;
import hana.common.utils.RandomStringGenerator;
import hana.member.domain.Member;
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

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@TypeInfo(name = "StudentController", description = "학생 컨트롤러")
@RestController
@Tag(name = "Student", description = "학생")
@RequestMapping("api/v1")
public class StudentController {

    private final StudentService studentService;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "readStudent", description = "학생증을 조회합니다.")
    @GetMapping("/students")
    @AuthenticatedMember
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
        Member member = jwtUtils.getMember();
        StudentReadResDto response = studentService.getStudentByMemberIdx(member.getMemberIdx());
        return ResponseEntity.ok(response);
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
        try {
            String qrContent = RandomStringGenerator.generateRandomString(10);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            String base64QrImage = Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());

            StudentQrReadResDto response = StudentQrReadResDto.builder()
                    .data(StudentQrReadResDto.Data.builder()
                            .qrImage("data:image/png;base64," + base64QrImage)
                            .build())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 서버 오류
        }
    }

    public StudentController(StudentService studentService, JwtUtils jwtUtils) {
        this.studentService = studentService;
        this.jwtUtils = jwtUtils;
    }
}
