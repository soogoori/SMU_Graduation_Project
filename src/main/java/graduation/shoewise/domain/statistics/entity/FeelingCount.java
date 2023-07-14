package graduation.shoewise.domain.statistics.entity;

import com.querydsl.core.annotations.QueryProjection;
import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.ReviewInfo;

public class FeelingCount implements Countable {

    private final Feeling feeling;
    private final long count;

    @QueryProjection
    public FeelingCount(Feeling feeling, long count) {
        this.feeling = feeling;
        this.count = count;
    }

    @Override
    public ReviewInfo getValue() {
        return feeling;
    }

    @Override
    public Long getCount() {
        return count;
    }
}
