package hana.story.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QStoryComment is a Querydsl query type for StoryComment */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoryComment extends EntityPathBase<StoryComment> {

    private static final long serialVersionUID = 1894595385L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoryComment storyComment = new QStoryComment("storyComment");

    public final hana.common.entity.QBaseEntity _super;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final hana.member.domain.QMember createdBy;

    // inherited
    public final BooleanPath deletedYn;

    public final QStory story;

    public final StringPath storyCommentContent = createString("storyCommentContent");

    public final NumberPath<Long> storyCommentIdx = createNumber("storyCommentIdx", Long.class);

    public final QStoryComment storyCommentParent;

    // inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final hana.member.domain.QMember updatedBy;

    public QStoryComment(String variable) {
        this(StoryComment.class, forVariable(variable), INITS);
    }

    public QStoryComment(Path<? extends StoryComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoryComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoryComment(PathMetadata metadata, PathInits inits) {
        this(StoryComment.class, metadata, inits);
    }

    public QStoryComment(
            Class<? extends StoryComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.story =
                inits.isInitialized("story")
                        ? new QStory(forProperty("story"), inits.get("story"))
                        : null;
        this.storyCommentParent =
                inits.isInitialized("storyCommentParent")
                        ? new QStoryComment(
                                forProperty("storyCommentParent"), inits.get("storyCommentParent"))
                        : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }
}
