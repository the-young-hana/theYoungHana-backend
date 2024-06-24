package hana.student.dto;

import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudentQrReadResDto extends BaseResponse {
    private final Data data;

    @Builder
    public StudentQrReadResDto(Data data) {
        super();
        this.data = data;
    }

    @Getter
    public static class Data {
        private final String qrImage;

        @Builder
        public Data(String qrImage) {
            this.qrImage = qrImage;
        }
    }
}
