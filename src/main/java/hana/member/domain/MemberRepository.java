package hana.member.domain;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@TypeInfo(name = "MemberRepository", description = "회원 레포지토리")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @MethodInfo(name = "findByMemberIdx", description = "회원 Idx로 회원을 조회합니다.")
    Member findByMemberIdx(Long memberIdx);

    @MethodInfo(name = "findByMemberPw", description = "회원 PW로 회원을 조회합니다.")
    Member findByMemberPw(String memberPw);
}
