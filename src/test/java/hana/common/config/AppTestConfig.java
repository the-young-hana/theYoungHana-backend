package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;

@TypeInfo(name = "AppTestConfig", description = "테스트 설정을 관리합니다.")
@Configuration
public class AppTestConfig implements BeforeAllCallback {
    @MethodInfo(name = "beforeAll", description = "테스트 실행 전 MySQL 컨테이너를 실행합니다.")
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        System.setProperty(
                "jwt.secret", "db308c664441568174c38810f78933ca3900393b46a4284f386f46d2a1b82bbb");
        System.setProperty("jwt.access-token-expiration", "300000");
        System.setProperty("jwt.refresh-token-expiration", "86400000");
        System.setProperty("cloud.aws.credentials.accessKey", "");
        System.setProperty("cloud.aws.credentials.secretKey", "");
        System.setProperty("cloud.aws.region.static", "");
        System.setProperty("cloud.aws.s3.bucket", "");
        System.setProperty("fcm.google_application_credentials", "");
        System.setProperty("fcm.url", "");
    }
}
