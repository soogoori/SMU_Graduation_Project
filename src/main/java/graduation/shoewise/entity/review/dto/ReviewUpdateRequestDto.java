package graduation.shoewise.entity.review.dto;

import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import graduation.shoewise.entity.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    private String content;
    private Fit fit;
    private Width width;
    private Feeling feeling;
    private Integer rating;

    @Builder
    public ReviewUpdateRequestDto(String content, Fit fit, Width width, Feeling feeling, Integer rating) {
        this.content = content;
        this.fit =fit;
        this.width =width;
        this.feeling = feeling;
        this.rating = rating;
    }

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
