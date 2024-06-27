package hana.account.service;

import hana.account.domain.Account;
import hana.account.domain.Transaction;
import hana.account.domain.TransactionRepository;
import hana.account.domain.TransactionTypeEnumType;
import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.dto.TransactionsReadResDto;
import hana.account.dto.TransactionsRemitCreateReqDto;
import hana.account.dto.TransactionsRemitCreateResDto;
import hana.college.domain.Dept;
import hana.college.service.DeptService;
import hana.common.annotation.TypeInfo;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@TypeInfo(name = "TransactionService", description = "거래 서비스")
@Service
@Slf4j
public class TransactionService {
    private final AccountService accountService;
    private final DeptService deptService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionsRemitCreateResDto remit(TransactionsRemitCreateReqDto dto) {

        // 내 계좌 출금, 거래 내역 생성(출금)
        Account myAccount = accountService.findByAccountIdx(dto.getMyAccountIdx());
        accountService.withdraw(dto.getMyAccountIdx(), dto.getAmount());

        Account receiveAccount;
        if (dto.getReceiveAccount()
                .startsWith("125934691")) { // 125910692는 일반 하나은행 계좌, 125934691은 가상 계좌로 가정
            Long accountIdx =
                    deptService.findAccountIdxByDeptAccountNumber(dto.getReceiveAccount());
            receiveAccount = accountService.findByAccountIdx(accountIdx);
        } else {
            receiveAccount = accountService.findByAccountNumber(dto.getReceiveAccount());
        }

        accountService.deposit(receiveAccount.getAccountNumber(), dto.getAmount());
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

    @Transactional
    public TransactionsReadResDto getTransactions(
            Long deptIdx, String startDate, String endDate, String type, String sort, Long page) {

        Dept dept = deptService.findByDeptIdx(deptIdx);
        Account deptAccount = dept.getAccount();

        List<DeptAccountTransactionResDto> transactions =
                transactionRepository.getTransactionsByDate(
                        deptAccount.getAccountIdx(), startDate, endDate, type, sort, page);

        return TransactionsReadResDto.builder()
                .data(
                        TransactionsReadResDto.Data.builder()
                                .deptName(dept.getDeptName())
                                .deptAccountNumber(dept.getDeptAccountNumber())
                                .deptAccountBalance(deptAccount.getAccountBalance())
                                .deptAccountTransactions(transactions)
                                .build())
                .build();
    }

    @Transactional
    public List<DeptAccountTransactionResDto> getTransactionsByStory(Long storyIdx) {
        return transactionRepository.getTransactionsByStory(storyIdx);
    }

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountService accountService,
            DeptService deptService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.deptService = deptService;
    }
}
