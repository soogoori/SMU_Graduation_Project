package graduation.shoewise.entity.review.dto;

import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.user.User;
import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewSaveRequestDto {

    private String content;
    private String nickname;
    private Integer rating;
    private Feeling feeling;
    private Fit fit;
    private Width width;

    @Builder
    public ReviewSaveRequestDto(String content, String nickname, Integer rating, Feeling feeling, Fit fit, Width width) {
        this.content =content;
        this.nickname = nickname;
        this.rating = rating;
        this.feeling = feeling;
        this.fit = fit;
        this.width = width;
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
