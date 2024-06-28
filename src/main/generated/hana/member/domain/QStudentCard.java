package hana.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QStudentCard is a Querydsl query type for StudentCard */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudentCard extends EntityPathBase<StudentCard> {

    private static final long serialVersionUID = -1638955405L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudentCard studentCard = new QStudentCard("studentCard");

    public final hana.common.entity.QBaseEntity _super;

    public final hana.college.domain.QCollege college;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final StringPath studentCardBackImage = createString("studentCardBackImage");

    public final StringPath studentCardFrontImage = createString("studentCardFrontImage");

    public final NumberPath<Long> studentCardIdx = createNumber("studentCardIdx", Long.class);

    public final BooleanPath studentCardIsVertical = createBoolean("studentCardIsVertical");

    public final StringPath studentCardName = createString("studentCardName");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final QMember updatedBy;

    public QStudentCard(String variable) {
        this(StudentCard.class, forVariable(variable), INITS);
    }

    public QStudentCard(Path<? extends StudentCard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudentCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudentCard(PathMetadata metadata, PathInits inits) {
        this(StudentCard.class, metadata, inits);
    }

    public QStudentCard(Class<? extends StudentCard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.college =
                inits.isInitialized("college")
                        ? new hana.college.domain.QCollege(
                                forProperty("college"), inits.get("college"))
                        : null;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
