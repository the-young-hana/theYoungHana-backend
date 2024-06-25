package hana.member.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StudentReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final Long studentIdx;
        private final String studentName;
        private final String studentId;
        private final String studentCollege;
        private final String studentDept;
        private final String studentCardImage;
        private final Boolean isVertical;

        @Builder
        public Data(
                Long studentIdx,
                String studentName,
                String studentId,
                String studentCollege,
                String studentDept,
                String studentCardImage,
                Boolean isVertical) {
            this.studentIdx = studentIdx;
            this.studentName = studentName;
            this.studentId = studentId;
            this.studentCollege = studentCollege;
            this.studentDept = studentDept;
            this.studentCardImage = studentCardImage;
            this.isVertical = isVertical;
        }
    }
}
