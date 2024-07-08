package hana.account.service;

import hana.account.domain.Account;
import hana.account.domain.AccountRepository;
import hana.account.dto.AccountPwCheckReqDto;
import hana.account.dto.AccountPwCheckResDto;
import hana.account.dto.AccountReadResDto;
import hana.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountReadResDto getMyAccounts(Long memberIdx) {
        List<Account> accounts =
                accountRepository.findByMember_MemberIdxAndDeletedYnFalse(memberIdx);
        List<AccountReadResDto.Data> myAccountInfoReadDtoList = new ArrayList<>();
        for (Account account : accounts) {
            myAccountInfoReadDtoList.add(
                    AccountReadResDto.Data.builder()
                            .accountNumber(account.getAccountNumber())
                            .accountIdx(account.getAccountIdx())
                            .accountName(account.getAccountName())
                            .accountBalance(account.getAccountBalance())
                            .build());
        }

        return AccountReadResDto.builder().data(myAccountInfoReadDtoList).build();
    }

    public Account findByAccountIdx(Long accountIdx) {
        return accountRepository
                .findById(accountIdx)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 계좌 정보입니다."));
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 계좌 정보입니다."));
    }

    // 학과 계좌 입금
    @CacheEvict(
            value = "DeptAccountInfoDto",
            key = "'deptAccount' + #deptIdx",
            cacheManager = "redisCacheManager")
    public void deposit(String accountNumber, Long amount, Long deptIdx) {
        // 계좌 없으면 예외처리
        Account account = findByAccountNumber(accountNumber);
        account.deposit(amount); // dynamic update
    }

    // 출금
    @CacheEvict(
            value = "DeptAccountInfoDto",
            key = "'deptAccount' + #deptIdx",
            cacheManager = "redisCacheManager")
    public void withdraw(Long accountIdx, Long amount, Long deptIdx) {
        // 계좌 없으면 예외처리
        Account account = findByAccountIdx(accountIdx);
        // 잔액 예외처리
        if (account.getAccountBalance() < amount) {
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        account.withdraw(amount); // dynamic update
    }

    @Transactional
    public AccountPwCheckResDto checkAccountPw(Member member, AccountPwCheckReqDto dto) {
        System.out.println(dto.getAccountIdx() + " " + dto.getAccountPw() + "~~~");
        Account account = findByAccountIdx(dto.getAccountIdx());

        System.out.println(
                account.getMember().getMemberIdx() + " " + member.getMemberIdx() + "~~~");

        if (!account.getMember().getMemberIdx().equals(member.getMemberIdx())) {
            throw new IllegalArgumentException("본인 계좌에서만 출금이 가능합니다.");
        }

        return AccountPwCheckResDto.builder()
                .data(
                        AccountPwCheckResDto.Data.builder()
                                .isPwCorrect(account.getAccountPw().equals(dto.getAccountPw()))
                                .build())
                .build();
    }

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
