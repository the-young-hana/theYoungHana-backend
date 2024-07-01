package hana.notification.controller;

import hana.common.Service.FCMService;
import hana.common.annotation.TypeInfo;
import hana.common.dto.Notification.FcmSendReqDto;
import hana.notification.dto.NotificationTestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "NotificationController", description = "알림 컨트롤러")
@RestController
@Tag(name = "Notification", description = "알림")
@RequestMapping("api/v1")
public class NotificationController {
    private final FCMService fcmService;

//    @PostMapping("/notificationTest")
//    public ResponseEntity<NotificationTestDto<Object>> pushMessage(
//            @RequestBody @Validated FcmSendReqDto fcmSendReqDto) throws IOException {
//        int result = fcmService.sendMessageTo(fcmSendReqDto);
//
//        NotificationTestDto<Object> test =
//                NotificationTestDto.builder()
//                        .result(result)
//                        .resultCode(200)
//                        .resultMessage("알림 성공했습니다.")
//                        .build();
//        return new ResponseEntity<>(test, HttpStatus.OK);
//    }

    public NotificationController(FCMService fcmService) {
        this.fcmService = fcmService;
    }
}
