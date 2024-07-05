package hana.member.dto;

import hana.common.dto.BaseResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberReadNoticesResDto extends BaseResponse {
    private final Data data;

    @Builder
    public MemberReadNoticesResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final List<Notice> notices;

        @Builder
        public Data(List<Notice> notices) {
            this.notices = notices;
        }

        @Getter
        public static class Notice {
            private final Long noticeIdx;
            private final String noticeTitle;
            private final String noticeContent;
            private final String noticeCategory;

            @Builder
            public Notice(
                    Long noticeIdx,
                    String noticeTitle,
                    String noticeContent,
                    String noticeCategory) {
                this.noticeIdx = noticeIdx;
                this.noticeTitle = noticeTitle;
                this.noticeContent = noticeContent;
                this.noticeCategory = noticeCategory;
            }
        }
    }
}
