package hana.college.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QCollege is a Querydsl query type for College */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollege extends EntityPathBase<College> {

    private static final long serialVersionUID = 1025859398L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCollege college = new QCollege("college");

    public final hana.common.entity.QBaseEntity _super;

    public final NumberPath<Long> collegeIdx = createNumber("collegeIdx", Long.class);

    public final StringPath collegeImage = createString("collegeImage");

    public final StringPath collegeName = createString("collegeName");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QCollege(String variable) {
        this(College.class, forVariable(variable), INITS);
    }

    public QCollege(Path<? extends College> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCollege(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCollege(PathMetadata metadata, PathInits inits) {
        this(College.class, metadata, inits);
    }

    public QCollege(Class<? extends College> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
