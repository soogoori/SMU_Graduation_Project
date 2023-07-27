package graduation.shoewise.domain.purchase;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPurchase is a Querydsl query type for Purchase
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPurchase extends EntityPathBase<Purchase> {

    private static final long serialVersionUID = -1701101245L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPurchase purchase = new QPurchase("purchase");

    public final EnumPath<graduation.shoewise.domain.review.entity.Fit> fit = createEnum("fit", graduation.shoewise.domain.review.entity.Fit.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public final graduation.shoewise.domain.user.QUser user;

    public QPurchase(String variable) {
        this(Purchase.class, forVariable(variable), INITS);
    }

    public QPurchase(Path<? extends Purchase> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPurchase(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPurchase(PathMetadata metadata, PathInits inits) {
        this(Purchase.class, metadata, inits);
    }

    public QPurchase(Class<? extends Purchase> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new graduation.shoewise.domain.user.QUser(forProperty("user")) : null;
    }

}

