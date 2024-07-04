package hana.college.service;

import hana.account.domain.Account;
import hana.account.dto.DeptAccountInfoDto;
import hana.college.domain.Dept;
import hana.college.domain.DeptRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
    private final DeptRepository deptRepository;

    public Dept findByDeptIdx(Long deptIdx) {
        return deptRepository
                .findById(deptIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학과입니다."));
    }

    public Dept findDeptByDeptIdx(Long deptIdx) {
        return deptRepository
                .findDeptByDeptIdx(deptIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학과입니다."));
    }

    public Long findAccountIdxByDeptAccountNumber(String deptAccountNumber) {
        return deptRepository
                .findByDeptAccountNumber(deptAccountNumber)
                .orElseThrow()
                .getAccount()
                .getAccountIdx();
    }

    @Cacheable(
            value = "DeptAccountInfoDto",
            key = "'deptAccount' + #deptIdx",
            unless = "#result == null",
            cacheManager = "redisCacheManager")
    public DeptAccountInfoDto getDeptAccountInfo(Long deptIdx) {
        Dept dept = findDeptByDeptIdx(deptIdx);
        Account account = dept.getAccount();
        Long accountIdx = account.getAccountIdx();

        return new DeptAccountInfoDto(
                dept.getDeptName(),
                account.getAccountNumber(),
                account.getAccountBalance(),
                accountIdx);
    }

    public DeptService(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }
}
