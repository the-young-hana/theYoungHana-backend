package hana.common.config;

import hana.common.vo.UserDetails;
import hana.member.domain.MemberToken;
import hana.member.service.MemberTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberTokenService memberTokenService;

    @Override
    public void doFilter(
            ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) servletRequest);

        if (token != null) {
            try {
                MemberToken memberToken = memberTokenService.findByAccessToken(token);

                if (memberToken != null) {
                    UserDetails userDetails =
                            new UserDetails(memberToken.getMember(), memberToken.getStudent());
                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails, "", userDetails.getAuthorities()));
                }

                UserDetails userDetails =
                        new UserDetails(
                                jwtTokenProvider.getMember(token),
                                jwtTokenProvider.getStudent(token));
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException runtimeException) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                String jsonResponse =
                        String.format("{\"error\": \"%s\"}", runtimeException.getMessage());
                httpServletResponse.getWriter().write(jsonResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public JwtAuthenticationFilter(
            JwtTokenProvider jwtTokenProvider, MemberTokenService memberTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberTokenService = memberTokenService;
    }
}
