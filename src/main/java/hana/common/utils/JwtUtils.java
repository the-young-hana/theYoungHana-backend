package hana.common.utils;

import hana.common.vo.UserDetails;
import hana.member.domain.Member;
import hana.member.domain.Student;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getMember();
        }
        return null;
    }

    public Student getStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getStudent();
        }
        return null;
    }
}
