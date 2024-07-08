package hana.member.domain;

import hana.common.annotation.TypeInfo;
import hana.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@TypeInfo(name = "Notice", description = "알림")
@Entity
@Table(name = "notices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_idx", nullable = false)
    private Long noticeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx", nullable = false)
    private Member member;

    @Column(name = "notice_title", nullable = false, length = 50)
    private String noticeTitle;

    @Column(name = "notice_content", nullable = false)
    private String noticeContent;

    @Column(name = "notice_category", nullable = false, length = 30)
    private String noticeCategory;

    @Builder
    public Notice(Member member, String noticeTitle, String noticeContent, String noticeCategory) {
        this.member = member;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCategory = noticeCategory;
    }
}
