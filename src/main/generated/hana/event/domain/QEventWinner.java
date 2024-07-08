package hana.event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QEventWinner is a Querydsl query type for EventWinner */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventWinner extends EntityPathBase<EventWinner> {

    private static final long serialVersionUID = -293426107L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEventWinner eventWinner = new QEventWinner("eventWinner");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final QEventPrize eventPrize;

    public final NumberPath<Long> eventWinnerIdx = createNumber("eventWinnerIdx", Long.class);

    public final hana.member.domain.QStudent student;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QEventWinner(String variable) {
        this(EventWinner.class, forVariable(variable), INITS);
    }

    public QEventWinner(Path<? extends EventWinner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEventWinner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEventWinner(PathMetadata metadata, PathInits inits) {
        this(EventWinner.class, metadata, inits);
    }

    public QEventWinner(Class<? extends EventWinner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.eventPrize =
                inits.isInitialized("eventPrize")
                        ? new QEventPrize(forProperty("eventPrize"), inits.get("eventPrize"))
                        : null;
        this.student =
                inits.isInitialized("student")
                        ? new hana.member.domain.QStudent(
                                forProperty("student"), inits.get("student"))
                        : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
