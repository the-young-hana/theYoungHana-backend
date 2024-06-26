package hana.account.domain;

import hana.account.dto.DeptAccountTransactionResDto;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<DeptAccountTransactionResDto> getTransactions(
            Long accountIdx, String startDate, String endDate, String type, String sort, Long page);
}
