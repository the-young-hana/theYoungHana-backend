package hana.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.dto.notification.FcmMessageResDto;
import hana.common.dto.notification.FcmSendReqDto;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@TypeInfo(name = "FCMService", description = "FCM 서비스")
@Service
@RequiredArgsConstructor
public class FCMService {
    @Value("${fcm.url}")
    private String FCM_URL;

    @Value("${GOOGLE_PRIVATE_KEY}")
    private String GOOGLE_PRIVATE_KEY;

    @Value("${GOOGLE_CLIENT_EMAIL}")
    private String GOOGLE_CLIENT_EMAIL;

    @Value("${GOOGLE_CLIENT_ID}")
    private String GOOGLE_CLIENT_ID;

    private final ObjectMapper objectMapper;

    // push 메세지 처리를 수행하는 비지니스 로직
    // return 성공(1), 실패(0)
    @MethodInfo(name = "sendMessageTo", description = "FCM 메세지를 전송합니다.")
    public int sendMessageTo(FcmSendReqDto fcmSendReqDto) throws IOException {
        String message = makeMessage(fcmSendReqDto);
        URL url = new URL(FCM_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        String accessToken = getAccessToken();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        httpURLConnection.setDoOutput(true);

        // Send the message
        try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
            byte[] input = message.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        int responseCode = httpURLConnection.getResponseCode();

        return responseCode == HttpURLConnection.HTTP_OK ? 1 : 0;
    }

    @MethodInfo(name = "getAccessToken", description = "FCM 액세스 토큰을 발급합니다.")
    // Firebase Admin SDK의 비공개 키를 참조하여 Bearer 토큰 발급
    private String getAccessToken() throws IOException {
        String credentialsJson =
                String.format(
                        "{"
                                + "\"type\": \"service_account\","
                                + "\"project_id\": \"theyounghana-ff6c3\","
                                + "\"private_key_id\": \"%s\","
                                + "\"private_key\": \"%s\","
                                + "\"client_email\": \"%s\","
                                + "\"client_id\": \"%s\","
                                + "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","
                                + "\"token_uri\": \"https://oauth2.googleapis.com/token\","
                                + "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\","
                                + "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-xsbw0@theyounghana-ff6c3.iam.gserviceaccount.com\","
                                + "\"universe_domain\": \"googleapis.com\""
                                + "}",
                        "b4fd60e21748ba12b3075645d0dbcfd860966030", // private_key_id 하드코딩
                        GOOGLE_PRIVATE_KEY.replace("\\n", "\n"), // private_key 환경 변수
                        GOOGLE_CLIENT_EMAIL, // client_email 환경 변수
                        GOOGLE_CLIENT_ID // client_id 환경 변수
                        );

        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(
                                new ByteArrayInputStream(
                                        credentialsJson.getBytes(StandardCharsets.UTF_8)))
                        .createScoped(
                                Arrays.asList(
                                        "https://www.googleapis.com/auth/firebase.messaging",
                                        "https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    @MethodInfo(name = "makeMessage", description = "FCM 메세지를 생성합니다.")
    // FCM 전송 정보를 기반으로 메세지를 구성 object -> string
    private String makeMessage(FcmSendReqDto fcmSendReqDto) throws JsonProcessingException {
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

        return objectMapper.writeValueAsString(fcmMessageResDto);
    }
}
