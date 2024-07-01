package hana.common.config;

import hana.common.vo.UserDetails;
import hana.member.domain.Member;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditAwareImpl implements AuditorAware<Member> {
    @Override
    public Optional<Member> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null
                && authentication.getPrincipal() instanceof UserDetails userDetails) {
            return Optional.of(userDetails.getMember());
        }
        return Optional.empty();
    }
}
