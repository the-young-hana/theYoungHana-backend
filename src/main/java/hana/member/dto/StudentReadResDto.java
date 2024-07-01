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
        private final String studentCollegeImage;
        private final String studentDept;
        private final String studentCardFrontImage;
        private final String studentCardBackImage;
        private final Boolean isVertical;

        @Builder
        public Data(
                Long studentIdx,
                String studentName,
                String studentId,
                String studentCollege,
                String studentCollegeImage,
                String studentDept,
                String studentCardFrontImage,
                String studentCardBackImage,
                Boolean isVertical) {
            this.studentIdx = studentIdx;
            this.studentName = studentName;
            this.studentId = studentId;
            this.studentCollege = studentCollege;
            this.studentCollegeImage = studentCollegeImage;
            this.studentDept = studentDept;
            this.studentCardFrontImage = studentCardFrontImage;
            this.studentCardBackImage = studentCardBackImage;
            this.isVertical = isVertical;
        }
    }
}
