package hana.account.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeptAccountInfoDto {
    private String deptName;
    private String deptAccountNumber;
    private Long deptAccountBalance;
    private Long deptAccountIdx;

    @Builder
    public DeptAccountInfoDto(
            String deptName,
            String deptAccountNumber,
            Long deptAccountBalance,
            Long deptAccountIdx) {
        this.deptName = deptName;
        this.deptAccountNumber = deptAccountNumber;
        this.deptAccountBalance = deptAccountBalance;
        this.deptAccountIdx = deptAccountIdx;
    }
}
