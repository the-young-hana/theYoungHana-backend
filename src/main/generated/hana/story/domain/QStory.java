package hana.story.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QStory is a Querydsl query type for Story */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStory extends EntityPathBase<Story> {

    private static final long serialVersionUID = -190738362L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStory story = new QStory("story");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final hana.college.domain.QDept dept;

    public final StringPath storyContent = createString("storyContent");

    public final NumberPath<Long> storyIdx = createNumber("storyIdx", Long.class);

    public final StringPath storyImageList = createString("storyImageList");

    public final StringPath storyTitle = createString("storyTitle");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QStory(String variable) {
        this(Story.class, forVariable(variable), INITS);
    }

    public QStory(Path<? extends Story> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStory(PathMetadata metadata, PathInits inits) {
        this(Story.class, metadata, inits);
    }

    public QStory(Class<? extends Story> type, PathMetadata metadata, PathInits inits) {
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
