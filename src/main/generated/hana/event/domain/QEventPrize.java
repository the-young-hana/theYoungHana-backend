package hana.event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEventPrize is a Querydsl query type for EventPrize
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventPrize extends EntityPathBase<EventPrize> {

    private static final long serialVersionUID = -1124044980L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventPrize eventPrize = new QEventPrize("eventPrize");

    public final hana.common.entity.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    //inherited
    public final BooleanPath deletedYn;

    public final QEvent event;

    public final NumberPath<Long> eventPrizeIdx = createNumber("eventPrizeIdx", Long.class);

    public final NumberPath<Long> eventPrizeLimit = createNumber("eventPrizeLimit", Long.class);

    public final StringPath eventPrizeName = createString("eventPrizeName");

    public final NumberPath<Long> eventPrizeRank = createNumber("eventPrizeRank", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QEventPrize(String variable) {
        this(EventPrize.class, forVariable(variable), INITS);
    }

    public QEventPrize(Path<? extends EventPrize> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventPrize(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventPrize(PathMetadata metadata, PathInits inits) {
        this(EventPrize.class, metadata, inits);
    }

    public QEventPrize(Class<? extends EventPrize> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event"), inits.get("event")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

