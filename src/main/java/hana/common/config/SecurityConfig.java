package hana.common.config;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.member.service.MemberTokenService;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final MemberTokenService memberTokenService;

    @MethodInfo(name = "filterChain", description = "스프링 시큐리티 필터 체인을 설정합니다.")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (sessionManagement) ->
                                sessionManagement.sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request ->
                                request.requestMatchers(
                                                "/",
                                                "api/**",
                                                "system/**",
                                                "swagger-ui/**",
                                                "/docs/**",
                                                "/v3/api-docs/**",
                                                "/plan/**",
                                                "/erd/**",
                                                "/prototype/**",
                                                "/ppt/**",
                                                "/wireframe/**",
                                                "/video/**",
                                                "/actuator/**",
                                                "/api/v1/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, memberTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider, MemberTokenService memberTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberTokenService = memberTokenService;
    }
}
