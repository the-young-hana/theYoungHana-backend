package hana.account.service;

import hana.account.domain.Account;
import hana.account.domain.Transaction;
import hana.account.domain.TransactionRepository;
import hana.account.domain.TransactionTypeEnumType;
import hana.account.dto.TransactionsRemitCreateReqDto;
import hana.account.dto.TransactionsRemitCreateResDto;
import hana.common.annotation.TypeInfo;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@TypeInfo(name = "TransactionService", description = "거래 서비스")
@Service
@Slf4j
public class TransactionService {
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionsRemitCreateResDto remit(TransactionsRemitCreateReqDto dto) {

        // 내 계좌 출금, 거래 내역 생성(출금)
        Account myAccount = accountService.findByAccountIdx(dto.getMyAccountIdx());
        accountService.withdraw(dto.getMyAccountIdx(), dto.getAmount());

        Account receiveAccount = accountService.findByAccountNumber(dto.getReceiveAccount());
        accountService.deposit(dto.getReceiveAccount(), dto.getAmount());

        // 거래 내역 생성(출금)
        transactionRepository.save(
                Transaction.builder()
                        .account(myAccount)
                        .transactionId(UUID.randomUUID().toString())
                        .transactionAmount(dto.getAmount())
                        .transactionBalance(myAccount.getAccountBalance())
                        .transactionName(receiveAccount.getMember().getMemberName())
                        .transactionTypeEnumType(TransactionTypeEnumType.출금)
                        .build());

        // 거래 내역 생성(입금)
        transactionRepository.save(
                Transaction.builder()
                        .account(receiveAccount)
                        .transactionId(UUID.randomUUID().toString())
                        .transactionAmount(dto.getAmount())
                        .transactionBalance(receiveAccount.getAccountBalance())
                        .transactionName(myAccount.getMember().getMemberName())
                        .transactionTypeEnumType(TransactionTypeEnumType.입금)
                        .build());

        return TransactionsRemitCreateResDto.builder()
                .data(
                        TransactionsRemitCreateResDto.Data.builder()
                                .myAccountName(myAccount.getAccountName())
                                .amount(dto.getAmount())
                                .receiverName(receiveAccount.getMember().getMemberName())
                                .build())
                .build();
    }

    public TransactionService(
            TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }
}
