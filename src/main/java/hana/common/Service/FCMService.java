package hana.common.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import hana.common.dto.Notification.FcmMessageResDto;
import hana.common.dto.Notification.FcmSendReqDto;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FCMService {
    @Value("${fcm.google_application_credentials}")
    private String GOOGLE_APPLICATION_CREDENTIALS;

    @Value("${fcm.url}")
    private String FCM_URL;

    // push 메세지 처리를 수행하는 비지니스 로직
    // return 성공(1), 실패(0)
    public int sendMessageTo(FcmSendReqDto fcmSendReqDto) throws IOException {
        String message = makeMessage(fcmSendReqDto);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity entity = new HttpEntity<>(message, headers);

        ResponseEntity response =
                restTemplate.exchange(FCM_URL, HttpMethod.POST, entity, String.class);

        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
    }

    // Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰 발급
    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(
                                new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS)
                                        .getInputStream())
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    // FCM 전송 정보를 기반으로 메세지를 구성 object -> string
    private String makeMessage(FcmSendReqDto fcmSendReqDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FcmMessageResDto fcmMessageResDto =
                FcmMessageResDto.builder()
                        .message(
                                FcmMessageResDto.Message.builder()
                                        .token(fcmSendReqDto.getToken())
                                        .notification(
                                                FcmMessageResDto.Notification.builder()
                                                        .title(fcmSendReqDto.getTitle())
                                                        .body(fcmSendReqDto.getBody())
                                                        .image(null)
                                                        .build())
                                        .build())
                        .validateOnly(false)
                        .build();

        return om.writeValueAsString(fcmMessageResDto);
    }
}
