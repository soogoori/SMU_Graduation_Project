package graduation.shoewise.domain.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1072947672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final graduation.shoewise.domain.QBaseEntity _super = new graduation.shoewise.domain.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final EnumPath<Feeling> feeling = createEnum("feeling", Feeling.class);

    public final EnumPath<Fit> fit = createEnum("fit", Fit.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final graduation.shoewise.domain.shoes.QShoes shoes;

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final graduation.shoewise.domain.user.QUser user;

    public final EnumPath<Width> width = createEnum("width", Width.class);

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shoes = inits.isInitialized("shoes") ? new graduation.shoewise.domain.shoes.QShoes(forProperty("shoes")) : null;
        this.user = inits.isInitialized("user") ? new graduation.shoewise.domain.user.QUser(forProperty("user")) : null;
    }

}

