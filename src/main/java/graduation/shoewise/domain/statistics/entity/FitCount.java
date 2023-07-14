package graduation.shoewise.domain.statistics.entity;

import com.querydsl.core.annotations.QueryProjection;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.ReviewInfo;

public class FitCount implements Countable {

    private final Fit fit;
    private final long count;

    @QueryProjection
    public FitCount(Fit fit, long count) {
        this.fit = fit;
        this.count = count;
    }

    @Override
    public ReviewInfo getValue() {
        return fit;
    }

    @Override
    public Long getCount() {
        return count;
    }
}
