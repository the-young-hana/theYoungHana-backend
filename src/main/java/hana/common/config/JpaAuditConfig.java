package hana.common.config;

import hana.member.domain.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class JpaAuditConfig {
    @Bean
    public AuditorAware<Member> auditorProvider() {
        return new AuditAwareImpl();
    }
}
