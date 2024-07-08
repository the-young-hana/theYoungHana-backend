package hana.common.vo;

import hana.member.domain.Member;
import hana.member.domain.Student;
import java.util.Collection;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final Member member;
    private final Student student;

    public Member getMember() {
        return member;
    }

    public Student getStudent() {
        return student;
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
    public UserDetails(Member member, Student student) {
        this.member = member;
        this.student = student;
    }
}
