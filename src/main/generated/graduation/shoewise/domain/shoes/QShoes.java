package graduation.shoewise.domain.shoes;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShoes is a Querydsl query type for Shoes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShoes extends EntityPathBase<Shoes> {

    private static final long serialVersionUID = 438456791L;

    public static final QShoes shoes = new QShoes("shoes");

    public final graduation.shoewise.domain.QBaseEntity _super = new graduation.shoewise.domain.QBaseEntity(this);

    public final NumberPath<Double> avgRating = createNumber("avgRating", Double.class);

    public final StringPath brand = createString("brand");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Double> feelingPercent = createNumber("feelingPercent", Double.class);

    public final NumberPath<Double> fitPercent = createNumber("fitPercent", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final StringPath productCode = createString("productCode");

    public final NumberPath<Integer> reviewCount = createNumber("reviewCount", Integer.class);

    public final NumberPath<Double> totalRating = createNumber("totalRating", Double.class);

    public final NumberPath<Double> widthPercent = createNumber("widthPercent", Double.class);

    public QShoes(String variable) {
        super(Shoes.class, forVariable(variable));
    }

    public QShoes(Path<? extends Shoes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShoes(PathMetadata metadata) {
        super(Shoes.class, metadata);
    }

}

