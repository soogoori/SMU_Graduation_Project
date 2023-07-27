package graduation.shoewise.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1312828669L;

    public static final QUser user = new QUser("user");

    public final graduation.shoewise.domain.QBaseEntity _super = new graduation.shoewise.domain.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<graduation.shoewise.domain.enums.ProviderType> provider = createEnum("provider", graduation.shoewise.domain.enums.ProviderType.class);

    public final StringPath providerId = createString("providerId");

    public final EnumPath<graduation.shoewise.domain.enums.RoleType> role = createEnum("role", graduation.shoewise.domain.enums.RoleType.class);

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

