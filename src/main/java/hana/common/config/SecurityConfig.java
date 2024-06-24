package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@TypeInfo(name = "SecurityConfig", description = "시큐리티 설정 클래스")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractHttpConfigurer<SecurityConfig, HttpSecurity> {
    @MethodInfo(name = "filterChain", description = "스프링 시큐리티 필터 체인을 설정합니다.")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(
                        corsCustomize ->
                                corsCustomize.configurationSource(
                                        request -> {
                                            CorsConfiguration config = new CorsConfiguration();
                                            config.setAllowCredentials(true);
                                            config.setAllowedOriginPatterns(
                                                    Collections.singletonList("*"));
                                            config.addAllowedMethod(HttpMethod.OPTIONS);
                                            config.addAllowedMethod(HttpMethod.GET);
                                            config.addAllowedMethod(HttpMethod.POST);
                                            config.addAllowedMethod(HttpMethod.PUT);
                                            config.addAllowedMethod(HttpMethod.DELETE);
                                            config.addAllowedHeader("Content-Type");
                                            config.setMaxAge(3600L);
                                            return config;
                                        }))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers(
                                                "/",
                                                "swagger-ui/**",
                                                "/docs/**",
                                                "/v3/api-docs/**",
                                                "/plan/**",
                                                "/prototype/**",
                                                "/wireframe/**",
                                                "/actuator/**",
                                                "/api/v1/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }
}
