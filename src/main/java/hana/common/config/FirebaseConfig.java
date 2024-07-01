package hana.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {
    @Value("${fcm.google_application_credentials}")
    private String GOOGLE_APPLICATION_CREDENTIALS;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
//        InputStream resource =
//                new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS).getInputStream();
        InputStream resource;
        if (GOOGLE_APPLICATION_CREDENTIALS.startsWith("{")) {
            // 환경 변수에 JSON 문자열이 직접 저장된 경우
            resource = new ByteArrayInputStream(GOOGLE_APPLICATION_CREDENTIALS.getBytes(StandardCharsets.UTF_8));
        } else {
            // JSON 파일 경로인 경우
            resource = new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS).getInputStream();
        }
        byte[] jsonBytes = resource.readAllBytes();
        String jsonString = new String(jsonBytes, StandardCharsets.UTF_8);

        Gson gson = new Gson();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        String jsonFomattedString = gson.toJson(jsonObject);

        InputStream jsonInputStream =
                new ByteArrayInputStream(jsonFomattedString.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options =
                FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(jsonInputStream))
                        .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
