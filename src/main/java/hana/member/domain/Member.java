package hana.member.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@TypeInfo(name = "Member", description = "회원 엔티티")
@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx", nullable = false)
    private Long memberIdx;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "member_pw", nullable = false, length = 10)
    private String memberPw;

    @Column(name = "member_name", nullable = false)
    private String memberName;

    @Column(name = "member_fcm_token", nullable = false)
    private String memberFcmToken;

    @Builder
    public Member(String memberId, String memberPw, String memberName, String memberFcmToken) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
        this.memberFcmToken = memberFcmToken;
    }
}
