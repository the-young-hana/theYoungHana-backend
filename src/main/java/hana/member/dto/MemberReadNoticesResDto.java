package hana.member.dto;

import hana.common.dto.BaseResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberReadNoticesResDto extends BaseResponse {
    private final List<Notices> data;

    @Builder
    public MemberReadNoticesResDto(List<Notices> data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Notices {
        private final String noticeCreatedAt;
        private final List<Notice> notices;

        @Builder
        public Notices(String noticeCreatedAt, List<Notice> notices) {
            this.noticeCreatedAt = noticeCreatedAt;
            this.notices = notices;
        }

        @Getter
        public static class Notice {
            private final Long noticeIdx;
            private final String noticeTitle;
            private final String noticeContent;
            private final String noticeCategory;
            private final LocalDateTime noticeCreatedAt;

            @Builder
            public Notice(
                    Long noticeIdx,
                    String noticeTitle,
                    String noticeContent,
                    String noticeCategory,
                    LocalDateTime noticeCreatedAt) {
                this.noticeIdx = noticeIdx;
                this.noticeTitle = noticeTitle;
                this.noticeContent = noticeContent;
                this.noticeCategory = noticeCategory;
                this.noticeCreatedAt = noticeCreatedAt;
            }
        }
    }
}
