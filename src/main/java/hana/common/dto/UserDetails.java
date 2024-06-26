package hana.common.dto;

import hana.member.domain.Member;
import java.util.Collection;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final Member member;

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public String getPassword() {
        return member.getMemberPw();
    }

    @Builder
    public UserDetails(Member member) {
        this.member = member;
    }
}
