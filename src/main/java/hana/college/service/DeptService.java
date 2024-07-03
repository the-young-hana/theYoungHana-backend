package hana.college.service;

import hana.college.domain.Dept;
import hana.college.domain.DeptRepository;
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

    public DeptService(DeptRepository deptRepository) {
        this.deptRepository = deptRepository;
    }
}
