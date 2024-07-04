package hana.account.service;

import hana.account.domain.Account;
import hana.account.domain.Transaction;
import hana.account.domain.TransactionRepository;
import hana.account.domain.TransactionTypeEnumType;
import hana.account.dto.*;
import hana.college.domain.Dept;
import hana.college.service.DeptService;
import hana.common.annotation.TypeInfo;
import hana.common.exception.AccessDeniedCustomException;
import hana.common.utils.JwtUtils;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@TypeInfo(name = "TransactionService", description = "거래 서비스")
@Service
@Slf4j
public class TransactionService {
    private final AccountService accountService;
    private final DeptService deptService;
    private final TransactionRepository transactionRepository;
    private final JwtUtils jwtUtils;

    @Transactional
    public TransactionsRemitCreateResDto remit(TransactionsRemitCreateReqDto dto) {

        // 내 계좌 출금, 거래 내역 생성(출금)
        Account myAccount = accountService.findByAccountIdx(dto.getMyAccountIdx());

        // 내 계좌인지 확인
        if (!Objects.equals(
                myAccount.getMember().getMemberIdx(), jwtUtils.getMember().getMemberIdx())) {

            throw new AccessDeniedCustomException();
        }

        accountService.withdraw(dto.getMyAccountIdx(), dto.getAmount(), dto.getDeptIdx());

        // 학과 계좌 입금
        Account receiveAccount;
        if (dto.getReceiveAccount()
                .startsWith("125934691")) { // 125910692는 일반 하나은행 계좌, 125934691은 가상 계좌로 가정
            Long accountIdx =
                    deptService.findAccountIdxByDeptAccountNumber(dto.getReceiveAccount());
            receiveAccount = accountService.findByAccountIdx(accountIdx);
        } else {
            receiveAccount = accountService.findByAccountNumber(dto.getReceiveAccount());
        }

        accountService.deposit(
                receiveAccount.getAccountNumber(), dto.getAmount(), dto.getDeptIdx());

        // 거래 내역 생성(출금)
        transactionRepository.save(
                Transaction.builder()
                        .account(myAccount)
                        .transactionId(UUID.randomUUID().toString())
                        .transactionAmount(dto.getAmount())
                        .transactionBalance(myAccount.getAccountBalance())
                        .transactionName(myAccount.getMember().getMemberName())
                        .transactionTypeEnumType(TransactionTypeEnumType.출금)
                        .build());

        // 거래 내역 생성(입금)
        transactionRepository.save(
                Transaction.builder()
                        .account(receiveAccount)
                        .transactionId(UUID.randomUUID().toString())
                        .transactionAmount(dto.getAmount())
                        .transactionBalance(receiveAccount.getAccountBalance())
                        .transactionName(receiveAccount.getMember().getMemberName())
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
    public List<TransactionsByDateResDto> getTransactions(
            Long accountIdx,
            String startDate,
            String endDate,
            String type,
            String sort,
            Long page) {

        return transactionRepository.getTransactionsByDate(
                accountIdx, startDate, endDate, type, sort, page);
    }

    @Cacheable(
            value = "DeptAccountInfoDto",
            key = "'deptAccount' + #deptIdx",
            unless = "#result == null",
            cacheManager = "redisCacheManager")
    public DeptAccountInfoDto getDeptAccountInfo(Long deptIdx) {
        Dept dept = deptService.findDeptByDeptIdx(deptIdx);
        Account account = dept.getAccount();
        Long accountIdx = account.getAccountIdx();

        return new DeptAccountInfoDto(
                dept.getDeptName(),
                account.getAccountNumber(),
                account.getAccountBalance(),
                accountIdx);
    }

    @Transactional(readOnly = true)
    public List<DeptAccountTransactionResDto> getTransactionsByStory(Long storyIdx) {
        return transactionRepository.getTransactionsByStory(storyIdx);
    }

    @Transactional
    public List<TransactionsByDateResDto> getTransactionsForStoryDetail(Long storyIdx) {
        return transactionRepository.getTransactionsForStoryDetail(storyIdx);
    }

    public Transaction findByTransactionIdx(Long transactionIdx) {
        return transactionRepository
                .findById(transactionIdx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 거래 정보입니다."));
    }

    public TransactionService(
            TransactionRepository transactionRepository,
            AccountService accountService,
            DeptService deptService,
            JwtUtils jwtUtils) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.deptService = deptService;
        this.jwtUtils = jwtUtils;
    }
}
