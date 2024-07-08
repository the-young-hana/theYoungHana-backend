package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.member.domain.MemberToken;
import hana.member.domain.MemberTokenRepository;
import java.util.List;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "MemberTokenService", description = "회원 토큰 서비스")
@Service
public class MemberTokenService {
    private final MemberTokenRepository memberTokenRepository;

    @MethodInfo(name = "save", description = "회원 토큰을 저장합니다.")
    public void save(MemberToken memberToken) {
        MemberToken savedMemberToken =
                memberTokenRepository.findByMemberIdx(memberToken.getMemberIdx());

        if (savedMemberToken != null) {
            memberTokenRepository.delete(savedMemberToken);
        }

        memberTokenRepository.save(memberToken);
    }

    @MethodInfo(name = "findByMemberIdx", description = "회원 식별자로 회원 토큰을 조회합니다.")
    public MemberToken findByMemberIdx(Long memberIdx) {
        return memberTokenRepository.findByMemberIdx(memberIdx);
    }

    @MethodInfo(name = "findAllByDeptIdx", description = "부서 식별자로 회원 토큰 목록을 조회합니다.")
    public List<MemberToken> findAllByDeptIdx(Long deptIdx) {
        return memberTokenRepository.findAllByDeptIdx(deptIdx);
    }

    @Builder
    public MemberTokenService(MemberTokenRepository memberTokenRepository) {
        this.memberTokenRepository = memberTokenRepository;
    }
}
