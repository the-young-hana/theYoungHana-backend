package hana.member.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "MemberTokenRepository", description = "회원 토큰 레포지토리")
@Repository
public interface MemberTokenRepository extends CrudRepository<MemberToken, Long> {
    @MethodInfo(name = "findByMemberIdx", description = "회원 Idx로 회원 토큰을 조회합니다.")
    MemberToken findByMemberIdx(Long memberIdx);

    @MethodInfo(name = "findAllByDeptIdx", description = "부서 Idx로 회원 토큰 목록을 조회합니다.")
    List<MemberToken> findAllByDeptIdx(Long deptIdx);

    @MethodInfo(name = "findByAccessToken", description = "액세스 토큰으로 회원 토큰을 조회합니다.")
    MemberToken findByAccessToken(String accessToken);
}
