package hana.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.dto.notification.FcmMessageResDto;
import hana.common.dto.notification.FcmSendReqDto;
import hana.member.domain.Notice;
import hana.member.service.MemberService;
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

    @Value("${GOOGLE_PRIVATE_KEY_ID}")
    private String GOOGLE_PRIVATE_KEY_ID;

    @Value("${GOOGLE_CLIENT_EMAIL}")
    private String GOOGLE_CLIENT_EMAIL;

    @Value("${GOOGLE_CLIENT_ID}")
    private String GOOGLE_CLIENT_ID;

    private final ObjectMapper objectMapper;

    private final MemberService memberService;

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

        if (responseCode == HttpURLConnection.HTTP_OK) {
            memberService.createNotice(
                    Notice.builder()
                            .member(memberService.findByMemberIdx(fcmSendReqDto.getMemberIdx()))
                            .noticeTitle(fcmSendReqDto.getTitle())
                            .noticeContent(fcmSendReqDto.getBody())
                            .noticeCategory(fcmSendReqDto.getCategory())
                            .build());
            return responseCode;
        } else {
            return responseCode;
        }
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
                                + "\"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwehXs+daFKij5\\nP7WQitvEtjAudOLLV9IiaxMtXW9Hn6SllRQ2B7KPHNXRDiohvuUYYqjQagjot8pl\\nmMa36xE1yIKHBpok2emnYDe2DEbHgEa29/e0IvqRy9XlyddnySEF3Xz56VRHUKUV\\n2sWc7EiUGOFWbgdMljomiDouWvSOc6Gv4RS9UQJqnUyDQWMhgsuD5cDFcvnkMrWL\\n6qMuTbExAuhLFl63tqyhOt6+tc+Vd0DWjqMIPqK6GEPKlMO9j2mNrnwVh4It+/DN\\nhTEyr1OnY/Cg+U4YwtwUxQ/NvtAADpnOp5+PWhtazh+1V2CuA2d/nVpDGFaYU0a5\\n/ZBA+5sXAgMBAAECggEALr8oL62+dWfFksCdh7xAmsrn2GZ97P9GWneijNHDOGXB\\nZ1smD2nakq5AtS/bWTUH2n3H2e08juigZGkjunQ6CtUiPkIxcrGGgx+poj9+QDpq\\ngO0OYFkjppsn/caJmKReav9Qq6JzALEMCc35qqesUg6FIi/jHxZsMOjFAKJ3RYRK\\niz6ANIJcLRnfd8BBMQssYJ49uLsKaijxEIVZqZf2FfbvJxJjKr3ENRQGqI+3xvOF\\nngJ0ayGUdXfhk5NoZnDNOXfzP3KrLmMctsv4evcggwljT6dh6v5yrsvk3dHvi8F+\\ngAy8ofRNLuEF27EIB2GTNSL0BFV4Us60+RHjVpb0AQKBgQDrp2PUKiiZXUdvH1Nk\\n+oM/rRPEwI1l9ipo1vHGg3sTCeSRxqumsGA7YcZdN8gpyisrlv5FQYkeUi18OZ0R\\nVyUkGiKMnoAcdM6fJTkhosDohe5V5wAxW1lFZKbT49U9eG9R+Jh+/RtA/KM2ywTh\\nJGUvmex3EgLDP9PMGAyF5Y0mYQKBgQC/trgR0DrNzvTxL43U18Y/7VB5rMKclUSJ\\nNIYK7ZI1qO6aBt8f4v0I6xn3UFTAHyB73JWFc1UFmYT7xuznGz+/SIdD0vXH3Fui\\n6YaV8sxF8AA/C+yVgi9YNAg8ttkYWC6VcG/p2fV8pqEMWwCSDZr+sxuiq98fI4rs\\n83otY01EdwKBgFU8qa4/RYobpE/8W7TFrB2nI+Rh5YNA5BwhyzgLrMO5K1TTMJlN\\naLFx70ceeycqeiz9I6G3T7qI7OznBfoUtFl3npGQbzRbx7TmyOBytyYj845czMQN\\nj5+7BWDVIvdmDZXvvnEIagg6mWqjSnOJL5RlL5PuDtPCzYAgHr97xW9hAoGAMYif\\nVJLtrqFrbimwRq8dnQTPBzEY82Yp2B2HCKnLXivwwkFGQzHd2QENq3XI//7K4NXk\\nmB9td03sKzWBnlDPDrhJor+OG36W+qfFy1Z5axSQq7mD/jktzGddPXqs6vpVxuzP\\nLafQIs3WzJ9yrUnwoyBZ4pTvyFbhnUlnZvTCUQ8CgYEAn5pJ6e1Zvq1QAStkOBqA\\nk0Iq/k2xoVM0TjGaYPytoUW4egPeBxJOKwBoQuDteQ1DxF9HFKHh44xdHjTItH12\\ntSQjBH45CnLSc0BzvCzpXzeyH5239XpzgtLiTRfNK5By4Z1AHcHvECAmO295U4VN\\nxSmiDNBy0ryTU0bSTOyZzrw=\\n-----END PRIVATE KEY-----\\n\","
                                + "\"client_email\": \"%s\","
                                + "\"client_id\": \"%s\","
                                + "\"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\","
                                + "\"token_uri\": \"https://oauth2.googleapis.com/token\","
                                + "\"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\","
                                + "\"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-xsbw0@theyounghana-ff6c3.iam.gserviceaccount.com\","
                                + "\"universe_domain\": \"googleapis.com\""
                                + "}",
                        GOOGLE_PRIVATE_KEY_ID, GOOGLE_CLIENT_EMAIL, GOOGLE_CLIENT_ID);

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
    private String makeMessage(FcmSendReqDto fcmSendReqDto)
            throws JsonProcessingException, JsonProcessingException {
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
