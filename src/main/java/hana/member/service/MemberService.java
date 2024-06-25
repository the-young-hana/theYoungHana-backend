package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.dto.JwtToken;
import hana.common.utils.JwtUtils;
import hana.member.domain.Member;
import hana.member.domain.MemberRepository;
import hana.member.dto.MemberLoginReqDto;
import hana.member.dto.MemberLoginResDto;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "MemberService", description = "회원 서비스")
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtils jwtUtils;

    @MethodInfo(name = "login", description = "회원 로그인을 실행합니다.")
    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByMemberPw(memberLoginReqDto.getPassword());

        if (member == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        JwtToken jwtToken = jwtUtils.generateToken(member);

        return MemberLoginResDto.builder()
                .data(
                        MemberLoginResDto.Data.builder()
                                .accessToken(jwtToken.getAccessToken())
                                .refreshToken(jwtToken.getRefreshToken())
                                .build())
                .build();
    }

    @Builder
    public MemberService(JwtUtils jwtUtils, MemberRepository memberRepository) {
        this.jwtUtils = jwtUtils;
        this.memberRepository = memberRepository;
    }
}
