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
        private final Long memberIdx;
        private final String studentName;
        private final String studentId;
        private final String studentCollege;
        private final String studentCollegeImage;
        private final String studentDept;
        private final Long studentDeptIdx;
        private final String studentCardFrontImage;
        private final String studentCardBackImage;
        private final Boolean isVertical;
        private final Boolean isAdmin;

        @Builder
        public Data(
                Long studentIdx,
                Long memberIdx,
                String studentName,
                String studentId,
                String studentCollege,
                String studentCollegeImage,
                String studentDept,
                Long studentDeptIdx,
                String studentCardFrontImage,
                String studentCardBackImage,
                Boolean isVertical,
                Boolean isAdmin) {
            this.studentIdx = studentIdx;
            this.memberIdx = memberIdx;
            this.studentName = studentName;
            this.studentId = studentId;
            this.studentCollege = studentCollege;
            this.studentCollegeImage = studentCollegeImage;
            this.studentDept = studentDept;
            this.studentDeptIdx = studentDeptIdx;
            this.studentCardFrontImage = studentCardFrontImage;
            this.studentCardBackImage = studentCardBackImage;
            this.isVertical = isVertical;
            this.isAdmin = isAdmin;
        }
    }
}
