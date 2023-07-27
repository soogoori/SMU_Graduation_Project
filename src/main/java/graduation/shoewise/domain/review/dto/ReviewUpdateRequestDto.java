package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private String content;
    private Fit fit;
    private Width width;
    private Feeling feeling;
    private double rating;


    public Review toEntity() {
        return Review.builder()
                .content(content)
                .rating(rating)
                .feeling(feeling)
                .fit(fit)
                .width(width)
                .build();
    }
}
