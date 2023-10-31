package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private String content;
    private int size;
    private Fit fit;
    private Width width;
    private Feeling feeling;
    private double rating;


    public Review toEntity(User user, Shoes shoes) {
        return Review.builder()
                .user(user)
                .shoes(shoes)
                .content(content)
                .size(size)
                .rating(rating)
                .feeling(feeling)
                .fit(fit)
                .width(width)
                .build();
    }
}
