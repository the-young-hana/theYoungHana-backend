package hana.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginReqDto {
    private String password;

    @Builder
    public MemberLoginReqDto(String password) {
        this.password = password;
    }
}
