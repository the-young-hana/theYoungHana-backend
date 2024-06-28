package hana.college.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QDept is a Querydsl query type for Dept */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDept extends EntityPathBase<Dept> {

    private static final long serialVersionUID = 2015262454L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDept dept = new QDept("dept");

    public final hana.common.entity.QBaseEntity _super;

    public final hana.account.domain.QAccount account;

    public final QCollege college;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final StringPath deptAccountNumber = createString("deptAccountNumber");

    public final NumberPath<Long> deptIdx = createNumber("deptIdx", Long.class);

    public final StringPath deptName = createString("deptName");

    public final NumberPath<Long> deptPoint = createNumber("deptPoint", Long.class);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QDept(String variable) {
        this(Dept.class, forVariable(variable), INITS);
    }

    public QDept(Path<? extends Dept> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDept(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDept(PathMetadata metadata, PathInits inits) {
        this(Dept.class, metadata, inits);
    }

    public QDept(Class<? extends Dept> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.account =
                inits.isInitialized("account")
                        ? new hana.account.domain.QAccount(
                                forProperty("account"), inits.get("account"))
                        : null;
        this.college =
                inits.isInitialized("college")
                        ? new QCollege(forProperty("college"), inits.get("college"))
                        : null;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
