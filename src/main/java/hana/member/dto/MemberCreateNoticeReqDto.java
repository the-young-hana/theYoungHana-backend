package hana.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateNoticeReqDto {
    private final String noticeTitle;
    private final String noticeContent;

    @Builder
    public MemberCreateNoticeReqDto(String noticeTitle, String noticeContent) {
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
    }
}
