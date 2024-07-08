package hana.college.dto;

import hana.account.dto.DeptAccountInfoDto;
import hana.common.dto.BaseResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeptInfoReadResDto extends BaseResponse {
    private final DeptAccountInfoDto data;

    @Builder
    public DeptInfoReadResDto(DeptAccountInfoDto data) {
        super();
        this.data = data;
    }
}
