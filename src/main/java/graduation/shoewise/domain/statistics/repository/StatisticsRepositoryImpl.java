package graduation.shoewise.domain.statistics.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import graduation.shoewise.domain.statistics.entity.*;

import java.util.List;

import static graduation.shoewise.domain.review.QReview.review;

public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StatisticsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<FeelingCount> findFeelingCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QFeelingCount(review.feeling, review.id.count()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.feeling)
                .fetch();
    }

    @Override
    public List<FitCount> findFitCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QFitCount(review.fit, review.id.count()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.fit)
                .fetch();
    }

    @Override
    public List<WidthCount> findWidthCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QWidthCount(review.width, review.id.count()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.width)
                .fetch();
    }
}
