package hana.account.domain;

import static hana.account.domain.QTransaction.transaction;
import static hana.story.domain.QTransactionDetail.transactionDetail;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hana.account.dto.DeptAccountTransactionResDto;
import hana.account.dto.TransactionsByDateResDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final int PAGE_SIZE = 20;

    public TransactionRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<TransactionsByDateResDto> getTransactionsByDate(
            Long accountIdx,
            String startDate,
            String endDate,
            String type,
            String sort,
            Long page) {

        OrderSpecifier<?> primaryOrderSpecifier =
                sort.equals("오래된순") ? transaction.createdAt.asc() : transaction.createdAt.desc();
        OrderSpecifier<?> secondaryOrderSpecifier =
                sort.equals("오래된순")
                        ? transaction.transactionIdx.asc()
                        : transaction.transactionIdx.desc();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        Map<String, List<DeptAccountTransactionResDto>> transactionsByDate =
                queryFactory
                        .from(transaction)
                        .leftJoin(transactionDetail)
                        .on(
                                transaction.transactionIdx.eq(
                                        transactionDetail.transaction.transactionIdx))
                        .where(
                                transaction
                                        .account
                                        .accountIdx
                                        .eq(accountIdx)
                                        .and(
                                                transaction.createdAt.between(
                                                        startDateTime, endDateTime))
                                        .and(
                                                type.equals("전체")
                                                        ? null
                                                        : transaction.transactionTypeEnumType.eq(
                                                                TransactionTypeEnumType.valueOf(
                                                                        type))))
                        .orderBy(primaryOrderSpecifier, secondaryOrderSpecifier)
                        .offset((page - 1) * PAGE_SIZE)
                        .limit(PAGE_SIZE)
                        .transform(
                                GroupBy.groupBy(
                                                transaction
                                                        .createdAt
                                                        .stringValue()
                                                        .substring(0, 10))
                                        .as(
                                                GroupBy.list(
                                                        Projections.constructor(
                                                                DeptAccountTransactionResDto.class,
                                                                transaction.transactionIdx,
                                                                transaction.transactionId,
                                                                transaction.transactionName,
                                                                transaction.transactionAmount,
                                                                transaction.transactionBalance,
                                                                transaction.transactionTypeEnumType,
                                                                transactionDetail
                                                                        .transactionDetailIdx
                                                                        .isNotNull(),
                                                                transaction.createdAt))));

        return makeTransactionsByDate(transactionsByDate);
    }

    @Override
    public List<DeptAccountTransactionResDto> getTransactionsByStory(Long storyIdx) {

        return queryFactory
                .select(
                        Projections.constructor(
                                DeptAccountTransactionResDto.class,
                                transaction.transactionIdx,
                                transaction.transactionId,
                                transaction.transactionName,
                                transaction.transactionAmount,
                                transaction.transactionBalance,
                                transaction.transactionTypeEnumType,
                                transactionDetail.transactionDetailIdx.isNotNull(),
                                transaction.createdAt))
                .from(transaction)
                .leftJoin(transactionDetail)
                .on(transaction.transactionIdx.eq(transactionDetail.transaction.transactionIdx))
                .where(transactionDetail.story.storyIdx.eq(storyIdx))
                .orderBy(transaction.createdAt.asc())
                .fetch();
    }

    @Override
    public List<TransactionsByDateResDto> getTransactionsForStoryDetail(Long storyIdx) {

        Map<String, List<DeptAccountTransactionResDto>> transactionsByDate =
                queryFactory
                        .from(transaction)
                        .leftJoin(transactionDetail)
                        .on(
                                transaction.transactionIdx.eq(
                                        transactionDetail.transaction.transactionIdx))
                        .where(transactionDetail.story.storyIdx.eq(storyIdx))
                        .orderBy(transaction.createdAt.asc())
                        .transform(
                                GroupBy.groupBy(
                                                transaction
                                                        .createdAt
                                                        .stringValue()
                                                        .substring(0, 10))
                                        .as(
                                                GroupBy.list(
                                                        Projections.constructor(
                                                                DeptAccountTransactionResDto.class,
                                                                transaction.transactionIdx,
                                                                transaction.transactionId,
                                                                transaction.transactionName,
                                                                transaction.transactionAmount,
                                                                transaction.transactionBalance,
                                                                transaction.transactionTypeEnumType,
                                                                transactionDetail
                                                                        .transactionDetailIdx
                                                                        .isNotNull(),
                                                                transaction.createdAt))));
        return makeTransactionsByDate(transactionsByDate);
    }

    private List<TransactionsByDateResDto> makeTransactionsByDate(
            Map<String, List<DeptAccountTransactionResDto>> transactionsByDate) {
        return transactionsByDate.entrySet().stream()
                .map(entry -> new TransactionsByDateResDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
