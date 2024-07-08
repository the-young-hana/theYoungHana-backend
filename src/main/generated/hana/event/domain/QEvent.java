package hana.event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QEvent is a Querydsl query type for Event */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = 885072998L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final hana.college.domain.QDept dept;

    public final StringPath eventContent = createString("eventContent");

    public final DateTimePath<java.time.LocalDateTime> eventDatetime =
            createDateTime("eventDatetime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> eventEndDatetime =
            createDateTime("eventEndDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> eventFee = createNumber("eventFee", Long.class);

    public final DateTimePath<java.time.LocalDateTime> eventFeeEndDatetime =
            createDateTime("eventFeeEndDatetime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> eventFeeStartDatetime =
            createDateTime("eventFeeStartDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> eventIdx = createNumber("eventIdx", Long.class);

    public final StringPath eventImageList = createString("eventImageList");

    public final NumberPath<Long> eventLimit = createNumber("eventLimit", Long.class);

    public final DateTimePath<java.time.LocalDateTime> eventStartDatetime =
            createDateTime("eventStartDatetime", java.time.LocalDateTime.class);

    public final StringPath eventTitle = createString("eventTitle");

    public final EnumPath<EventEnumType> eventType = createEnum("eventType", EventEnumType.class);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.dept =
                inits.isInitialized("dept")
                        ? new hana.college.domain.QDept(forProperty("dept"), inits.get("dept"))
                        : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
