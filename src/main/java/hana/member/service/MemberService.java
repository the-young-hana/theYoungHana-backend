package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.config.JwtTokenProvider;
import hana.common.vo.JwtToken;
import hana.member.domain.Member;
import hana.member.domain.MemberRepository;
import hana.member.exception.UnlistedMemberException;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "MemberService", description = "회원 서비스")
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @MethodInfo(name = "login", description = "회원 로그인을 실행합니다.")
    public JwtToken login(String password) {
        Member member = memberRepository.findByMemberPw(password);

        if (member == null) {
            throw new UnlistedMemberException();
        }

        return jwtTokenProvider.generateToken(member);
    }

    @MethodInfo(name = "findByMemberPw", description = "회원 비밀번호로 회원 정보를 조회합니다.")
    public Member findByMemberPw(String memberPw) {
        return memberRepository.findByMemberPw(memberPw);
    }

    @Builder
    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
