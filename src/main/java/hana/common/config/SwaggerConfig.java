package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TypeInfo(name = "SwaggerConfig", description = "Swagger 설정 클래스")
@Configuration
@OpenAPIDefinition(
        info =
                @Info(
                        title = "더영하나 API",
                        version = "v1",
                        description = "더영하나에서 제공하는 API 목록입니다.",
                        license =
                                @License(
                                        name = "Apache 2.0",
                                        url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        servers = {
            @Server(url = "https://theyounghana.kro.kr", description = "스테이징 서버 (HTTPS)"),
            @Server(url = "http://theyounghana.kro.kr:8080", description = "스테이징 서버 (HTTP)"),
            @Server(url = "http://localhost:8080", description = "로컬 서버 (HTTP)")
        })
public class SwaggerConfig {
    @MethodInfo(
            name = "groupedOpenApi",
            description = "OpenAPI 문서를 생성하고 관리하기 위해 OpenAPI 스펙을 담은 객체를 반환합니다.")
    @Bean(name = "SwaggerConfig")
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/api/v1/**"};
        return GroupedOpenApi.builder().group("더영하나").pathsToMatch(paths).build();
    }
}
