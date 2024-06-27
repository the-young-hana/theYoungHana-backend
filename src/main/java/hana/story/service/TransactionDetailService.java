package hana.story.service;

import hana.account.domain.Transaction;
import hana.account.service.TransactionService;
import hana.story.domain.Story;
import hana.story.domain.TransactionDetail;
import hana.story.domain.TransactionDetailRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    private final TransactionService transactionService;

    public void saveTransactionDetails(List<Long> transactions, Story story) {
        for (Long transactionIdx : transactions) {
            Transaction transaction = transactionService.findByTransactionIdx(transactionIdx);
            // 중복 확인
            if (transactionDetailRepository.findByTransaction(transaction).isPresent())
                throw new IllegalArgumentException("이미 등록된 거래 내역입니다");
            transactionDetailRepository.save(
                    TransactionDetail.builder().transaction(transaction).story(story).build());
        }
    }

    public TransactionDetailService(
            TransactionDetailRepository transactionDetailRepository,
            TransactionService transactionService) {
        this.transactionDetailRepository = transactionDetailRepository;
        this.transactionService = transactionService;
    }
}
