package graduation.shoewise.domain.statistics.entity;

import com.querydsl.core.annotations.QueryProjection;
import graduation.shoewise.domain.review.entity.ReviewInfo;
import graduation.shoewise.domain.review.entity.Width;

public class WidthCount implements Countable {

    private final Width width;
    private final long count;

    @QueryProjection
    public WidthCount(Width width, long count) {
        this.width = width;
        this.count = count;
    }

    @Override
    public ReviewInfo getValue() {
        return width;
    }

    @Override
    public Long getCount() {
        return count;
    }
}
