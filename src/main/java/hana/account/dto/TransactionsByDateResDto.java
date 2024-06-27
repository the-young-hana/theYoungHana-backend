package hana.account.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TransactionsByDateResDto {
    private String date;
    private List<DeptAccountTransactionResDto> transactions;

    @Builder
    public TransactionsByDateResDto(String date, List<DeptAccountTransactionResDto> transactions) {
        this.date = date;
        this.transactions = transactions;
    }
}
