package hana.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateNoticeReqDto {
    private final String noticeTitle;
    private final String noticeContent;
    private final String noticeCategory;

    @Builder
    public MemberCreateNoticeReqDto(
            String noticeTitle, String noticeContent, String noticeCategory) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeCategory = noticeCategory;
    }
}
