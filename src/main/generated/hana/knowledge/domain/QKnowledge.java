package hana.knowledge.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QKnowledge is a Querydsl query type for Knowledge */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKnowledge extends EntityPathBase<Knowledge> {

    private static final long serialVersionUID = -648039962L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKnowledge knowledge = new QKnowledge("knowledge");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final StringPath knowledgeCategory = createString("knowledgeCategory");

    public final StringPath knowledgeContent = createString("knowledgeContent");

    public final NumberPath<Long> knowledgeIdx = createNumber("knowledgeIdx", Long.class);

    public final StringPath knowledgeImage = createString("knowledgeImage");

    public final StringPath knowledgeSummary = createString("knowledgeSummary");

    public final StringPath knowledgeTitle = createString("knowledgeTitle");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QKnowledge(String variable) {
        this(Knowledge.class, forVariable(variable), INITS);
    }

    public QKnowledge(Path<? extends Knowledge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKnowledge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKnowledge(PathMetadata metadata, PathInits inits) {
        this(Knowledge.class, metadata, inits);
    }

    public QKnowledge(Class<? extends Knowledge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
