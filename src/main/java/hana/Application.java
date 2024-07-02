package hana;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@TypeInfo(name = "Application", description = "어플리케이션 클래스")
@SpringBootApplication
@EnableScheduling
public class Application {

    @MethodInfo(name = "main", description = "어플리케이션을 실행합니다.")
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }
}
