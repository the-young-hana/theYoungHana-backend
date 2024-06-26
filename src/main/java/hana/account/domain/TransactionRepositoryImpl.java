package hana.account.domain;

import static hana.account.domain.QTransaction.transaction;
import static hana.story.domain.QTransactionDetail.transactionDetail;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hana.account.dto.DeptAccountTransactionResDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private static final int PAGE_SIZE = 20;

    public TransactionRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DeptAccountTransactionResDto> getTransactions(
            Long accountIdx,
            String startDate,
            String endDate,
            String type,
            String sort,
            Long page) {

        OrderSpecifier<?> orderSpecifier = transaction.createdAt.asc();
        if (sort.equals("오래된순")) {
            orderSpecifier = transaction.createdAt.desc();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

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
                .where(
                        transaction
                                .account
                                .accountIdx
                                .eq(accountIdx)
                                .and(transaction.createdAt.between(startDateTime, endDateTime))
                                .and(
                                        type.equals("전체")
                                                ? null
                                                : transaction.transactionTypeEnumType.eq(
                                                        TransactionTypeEnumType.valueOf(type))))
                .orderBy(orderSpecifier)
                .offset((page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .fetch();
    }
}
