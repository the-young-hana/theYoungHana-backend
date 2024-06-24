package hana;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.config.AppTestConfig;
import hana.common.config.MySQLTestConfig;
import hana.common.config.RedisTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@TypeInfo(name = "ApplicationTests", description = "어플리케이션 테스트 클래스")
@SpringBootTest(classes = Application.class)
@ExtendWith({AppTestConfig.class, MySQLTestConfig.class, RedisTestConfig.class})
class ApplicationTests {

    @MethodInfo(name = "contextLoads", description = "어플리케이션 컨텍스트가 정상적으로 로드되는지 확인합니다.")
    @Test
    void contextLoads() {}
}
