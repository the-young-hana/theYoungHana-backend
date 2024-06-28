package hana.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = 857110352L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotice notice = new QNotice("notice");

    public final hana.common.entity.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QMember createdBy;

    //inherited
    public final BooleanPath deletedYn;

    public final hana.event.domain.QEvent event;

    public final QMember member;

    public final StringPath noticeCategory = createString("noticeCategory");

    public final StringPath noticeContent = createString("noticeContent");

    public final NumberPath<Long> noticeIdx = createNumber("noticeIdx", Long.class);

    public final StringPath noticeTitle = createString("noticeTitle");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final QMember updatedBy;

    public QNotice(String variable) {
        this(Notice.class, forVariable(variable), INITS);
    }

    public QNotice(Path<? extends Notice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotice(PathMetadata metadata, PathInits inits) {
        this(Notice.class, metadata, inits);
    }

    public QNotice(Class<? extends Notice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.event = inits.isInitialized("event") ? new hana.event.domain.QEvent(forProperty("event"), inits.get("event")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

