package hana.story.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QTransactionDetail is a Querydsl query type for TransactionDetail */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransactionDetail extends EntityPathBase<TransactionDetail> {

    private static final long serialVersionUID = -1156391264L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransactionDetail transactionDetail =
            new QTransactionDetail("transactionDetail");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final QStory story;

    public final hana.account.domain.QTransaction transaction;

    public final NumberPath<Long> transactionDetailIdx =
            createNumber("transactionDetailIdx", Long.class);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QTransactionDetail(String variable) {
        this(TransactionDetail.class, forVariable(variable), INITS);
    }

    public QTransactionDetail(Path<? extends TransactionDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransactionDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransactionDetail(PathMetadata metadata, PathInits inits) {
        this(TransactionDetail.class, metadata, inits);
    }

    public QTransactionDetail(
            Class<? extends TransactionDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.story =
                inits.isInitialized("story")
                        ? new QStory(forProperty("story"), inits.get("story"))
                        : null;
        this.transaction =
                inits.isInitialized("transaction")
                        ? new hana.account.domain.QTransaction(
                                forProperty("transaction"), inits.get("transaction"))
                        : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
