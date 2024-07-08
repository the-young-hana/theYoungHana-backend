package hana.account.domain;

import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.dto.TransactionsByDateResDto;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<TransactionsByDateResDto> getTransactionsByDate(
            Long accountIdx, String startDate, String endDate, String type, String sort, Long page);

    List<DeptAccountTransactionResDto> getTransactionsByStory(Long storyIdx);

    List<TransactionsByDateResDto> getTransactionsForStoryDetail(Long storyIdx);
}
