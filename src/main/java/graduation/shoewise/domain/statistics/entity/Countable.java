package graduation.shoewise.domain.statistics.entity;

import graduation.shoewise.domain.review.entity.ReviewInfo;

public interface Countable {

    ReviewInfo getValue();
    Long getCount();

}
