package hana.account.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTransaction is a Querydsl query type for Transaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransaction extends EntityPathBase<Transaction> {

    private static final long serialVersionUID = -1944005449L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTransaction transaction = new QTransaction("transaction");

    public final hana.common.entity.QBaseEntity _super;

    public final QAccount account;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    //inherited
    public final BooleanPath deletedYn;

    public final NumberPath<Long> transactionAmount = createNumber("transactionAmount", Long.class);

    public final NumberPath<Long> transactionBalance = createNumber("transactionBalance", Long.class);

    public final StringPath transactionId = createString("transactionId");

    public final NumberPath<Long> transactionIdx = createNumber("transactionIdx", Long.class);

    public final StringPath transactionName = createString("transactionName");

    public final EnumPath<TransactionTypeEnumType> transactionTypeEnumType = createEnum("transactionTypeEnumType", TransactionTypeEnumType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QTransaction(String variable) {
        this(Transaction.class, forVariable(variable), INITS);
    }

    public QTransaction(Path<? extends Transaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTransaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTransaction(PathMetadata metadata, PathInits inits) {
        this(Transaction.class, metadata, inits);
    }

    public QTransaction(Class<? extends Transaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account"), inits.get("account")) : null;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

