package graduation.shoewise.domain.statistics.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import graduation.shoewise.domain.statistics.dto.PercentDto;
import graduation.shoewise.domain.statistics.dto.QPercentDto_FeelingPercentDto;
import graduation.shoewise.domain.statistics.dto.QPercentDto_FitPercentDto;
import graduation.shoewise.domain.statistics.dto.QPercentDto_WidthDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static graduation.shoewise.domain.review.entity.QReview.review;

@Component
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StatisticsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<PercentDto.FeelingPercentDto> findFeelingCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QPercentDto_FeelingPercentDto(review.feeling, review.fit.count().doubleValue()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.feeling)
                .fetch();
    }

    @Override
    public List<PercentDto.FitPercentDto> findFitCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QPercentDto_FitPercentDto(review.fit, review.fit.count().doubleValue()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.fit)
                .fetch();
    }

    @Override
    public List<PercentDto.WidthDto> findWidthCountByShoesId(Long shoesId) {
        return jpaQueryFactory.select(new QPercentDto_WidthDto(review.width, review.fit.count().doubleValue()))
                .from(review)
                .where(review.shoes.id.eq(shoesId))
                .groupBy(review.width)
                .fetch();
    }
}
