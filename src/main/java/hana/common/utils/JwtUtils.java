package hana.common.utils;

import hana.common.dto.UserDetails;
import hana.member.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    public Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getMember();
        }
        return null;
    }
}
