package hana.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QBaseEntity is a Querydsl query type for BaseEntity */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = 1101659934L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final QBaseTimeEntity _super = new QBaseTimeEntity(this);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final hana.member.domain.QMember createdBy;

    public final BooleanPath deletedYn = createBoolean("deletedYn");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final hana.member.domain.QMember updatedBy;

    public QBaseEntity(String variable) {
        this(BaseEntity.class, forVariable(variable), INITS);
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBaseEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBaseEntity(PathMetadata metadata, PathInits inits) {
        this(BaseEntity.class, metadata, inits);
    }

    public QBaseEntity(Class<? extends BaseEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.createdBy =
                inits.isInitialized("createdBy")
                        ? new hana.member.domain.QMember(
                                forProperty("createdBy"), inits.get("createdBy"))
                        : null;
        this.updatedBy =
                inits.isInitialized("updatedBy")
                        ? new hana.member.domain.QMember(
                                forProperty("updatedBy"), inits.get("updatedBy"))
                        : null;
    }
}
