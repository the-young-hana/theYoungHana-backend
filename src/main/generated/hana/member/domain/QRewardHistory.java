package hana.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRewardHistory is a Querydsl query type for RewardHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRewardHistory extends EntityPathBase<RewardHistory> {

    private static final long serialVersionUID = -696536339L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRewardHistory rewardHistory = new QRewardHistory("rewardHistory");

    public final hana.common.entity.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QMember createdBy;

    //inherited
    public final BooleanPath deletedYn;

    public final EnumPath<RewardCategoryEnumType> rewardCategory = createEnum("rewardCategory", RewardCategoryEnumType.class);

    public final NumberPath<Long> rewardHistoriesIdx = createNumber("rewardHistoriesIdx", Long.class);

    public final QStudent student;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final QMember updatedBy;

    public QRewardHistory(String variable) {
        this(RewardHistory.class, forVariable(variable), INITS);
    }

    public QRewardHistory(Path<? extends RewardHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRewardHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRewardHistory(PathMetadata metadata, PathInits inits) {
        this(RewardHistory.class, metadata, inits);
    }

    public QRewardHistory(Class<? extends RewardHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.student = inits.isInitialized("student") ? new QStudent(forProperty("student"), inits.get("student")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

