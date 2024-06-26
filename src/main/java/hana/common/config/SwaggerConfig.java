package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TypeInfo(name = "SwaggerConfig", description = "Swagger 설정 클래스")
@Configuration
@OpenAPIDefinition(
        info =
                @Info(
                        title = "하나 V API",
                        version = "v1",
                        description = "하나 V에서 제공하는 API 목록입니다.",
                        license =
                                @License(
                                        name = "Apache 2.0",
                                        url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        servers = {
            @Server(url = "https://theyounghana.kro.kr", description = "스테이징 서버 (HTTPS)"),
            @Server(url = "http://theyounghana.kro.kr:8080", description = "스테이징 서버 (HTTP)"),
            @Server(url = "http://localhost:8080", description = "로컬 서버 (HTTP)")
        })
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class SwaggerConfig {
    @MethodInfo(name = "groupedOpenApi", description = "Swagger API 그룹을 설정합니다.")
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder().group("Hana V API").pathsToMatch(paths).build();
    }

    @MethodInfo(name = "customOpenAPI", description = "Security 설정을 포함한 OpenAPI 객체를 생성합니다.")
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                                .type(
                                                        io.swagger.v3.oas.models.security
                                                                .SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
