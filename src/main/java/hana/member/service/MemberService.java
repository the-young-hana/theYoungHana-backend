package hana.member.service;

import hana.common.annotation.MethodInfo;
import hana.common.annotation.TypeInfo;
import hana.common.config.JwtTokenProvider;
import hana.common.vo.JwtToken;
import hana.member.domain.Member;
import hana.member.domain.MemberRepository;
import hana.member.domain.Notice;
import hana.member.domain.NoticeRepository;
import hana.member.exception.UnlistedMemberException;
import java.util.List;
import lombok.Builder;
import org.springframework.stereotype.Service;

@TypeInfo(name = "MemberService", description = "회원 서비스")
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final NoticeRepository noticeRepository;

    @MethodInfo(name = "login", description = "회원 로그인을 실행합니다.")
    public JwtToken login(String password) {
        Member member = memberRepository.findByMemberPw(password);

        if (member == null) {
            throw new UnlistedMemberException();
        }

        return jwtTokenProvider.generateToken(member);
    }

    @MethodInfo(name = "findByMemberIdx", description = "회원 번호로 회원 정보를 조회합니다.")
    public Member findByMemberIdx(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElse(null);
    }

    @MethodInfo(name = "findByMemberPw", description = "회원 비밀번호로 회원 정보를 조회합니다.")
    public Member findByMemberPw(String memberPw) {
        return memberRepository.findByMemberPw(memberPw);
    }

    @MethodInfo(name = "readNotices", description = "알림 목록을 조회합니다.")
    public List<Notice> readNotices(Long memberIdx) {
        return noticeRepository.findByMemberIdx(memberIdx);
    }

    @MethodInfo(name = "createNotice", description = "알림을 생성합니다.")
    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @MethodInfo(name = "deleteNotice", description = "알림을 삭제합니다.")
    public void deleteNotice(Long noticeIdx) {
        noticeRepository.deleteById(noticeIdx);
    }

    @Builder
    public MemberService(
            MemberRepository memberRepository,
            JwtTokenProvider jwtTokenProvider,
            NoticeRepository noticeRepository) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.noticeRepository = noticeRepository;
    }
}
