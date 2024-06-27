package hana.common.utils;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.vo.UserDetails;
import hana.member.domain.Member;
import hana.member.domain.Student;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@TypeInfo(name = "JwtUtils", description = "JWT 유틸리티")
@Component
public class JwtUtils {
    @MethodInfo(name = "getMember", description = "로그인한 회원 정보를 반환합니다.")
    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getMember();
        }
        return null;
    }

    @MethodInfo(name = "getStudent", description = "로그인한 학생 정보를 반환합니다.")
    public Student getStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getStudent();
        }
        return null;
    }
}
