package hana.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = 1087090883L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudent student = new QStudent("student");

    public final hana.common.entity.QBaseEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final QMember createdBy;

    //inherited
    public final BooleanPath deletedYn;

    public final hana.college.domain.QDept dept;

    public final QMember member;

    public final NumberPath<Integer> studentGrade = createNumber("studentGrade", Integer.class);

    public final StringPath studentId = createString("studentId");

    public final NumberPath<Long> studentIdx = createNumber("studentIdx", Long.class);

    public final BooleanPath studentIsAdmin = createBoolean("studentIsAdmin");

    public final StringPath studentName = createString("studentName");

    public final NumberPath<Integer> studentPoint = createNumber("studentPoint", Integer.class);

    public final EnumPath<StudentStatusEnumType> studentStatus = createEnum("studentStatus", StudentStatusEnumType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt;

    // inherited
    public final QMember updatedBy;

    public QStudent(String variable) {
        this(Student.class, forVariable(variable), INITS);
    }

    public QStudent(Path<? extends Student> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudent(PathMetadata metadata, PathInits inits) {
        this(Student.class, metadata, inits);
    }

    public QStudent(Class<? extends Student> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new hana.common.entity.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.deletedYn = _super.deletedYn;
        this.dept = inits.isInitialized("dept") ? new hana.college.domain.QDept(forProperty("dept"), inits.get("dept")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.updatedAt = _super.updatedAt;
        this.updatedBy = _super.updatedBy;
    }

}

