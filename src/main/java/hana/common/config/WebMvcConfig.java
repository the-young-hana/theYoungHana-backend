package hana.common.config;

import hana.common.annotation.TypeInfo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@TypeInfo(name = "WebMvcConfig", description = "웹 MVC 설정 클래스")
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해 CORS 설정을 적용
        registry.addMapping("/**")
                // 모든 출처에서 오는 요청을 허용
                .allowedOrigins(
                        "http://theyounghana.o-r.kr/",
                        "https://theyounghana.o-r.kr/",
                        "http://localhost:3000")
                // 모든 HTTP 메소드(GET, POST, PUT, DELETE 등)를 허용
                .allowedMethods("*")
                // 쿠키 전송 허용
                .allowCredentials(true)
                // 모든 헤더 허용
                .allowedHeaders("*");
    }
}
