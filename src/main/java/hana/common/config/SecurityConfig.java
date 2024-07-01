package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@TypeInfo(name = "SecurityConfig", description = "시큐리티 설정 클래스")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends AbstractHttpConfigurer<SecurityConfig, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;

    @MethodInfo(name = "filterChain", description = "스프링 시큐리티 필터 체인을 설정합니다.")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
//                .cors(
//                        corsCustomize ->
//                                corsCustomize.configurationSource(
//                                        request -> {
//                                            CorsConfiguration config = new CorsConfiguration();
//                                            config.setAllowCredentials(true);
//                                            config.setAllowedOriginPatterns(
//                                                    Collections.singletonList("*"));
//                                            config.addAllowedMethod(HttpMethod.OPTIONS);
//                                            config.addAllowedMethod(HttpMethod.GET);
//                                            config.addAllowedMethod(HttpMethod.POST);
//                                            config.addAllowedMethod(HttpMethod.PUT);
//                                            config.addAllowedMethod(HttpMethod.DELETE);
//                                            config.addAllowedHeader("Content-Type");
//                                            config.setMaxAge(3600L);
//                                            return config;
//                                        }))
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
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*")); // 허용할 HTTP header
        config.setAllowedMethods(Collections.singletonList("*")); // 허용할 HTTP method
        config.setAllowedOriginPatterns(
                Arrays.asList(
                        "http://localhost:3000",
                        "http://theyounghana.o-r.kr",
                        "https://theyounghana.o-r.kr")); // 허용할 출처
        config.setAllowCredentials(true); // 쿠키 인증 요청 허용
        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
