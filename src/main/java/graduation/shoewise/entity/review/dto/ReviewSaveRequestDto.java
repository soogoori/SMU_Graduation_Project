package graduation.shoewise.entity.review.dto;

import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import graduation.shoewise.entity.shoes.Shoes;
import graduation.shoewise.entity.user.User;
import lombok.Data;
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

    public Review toEntity(User user, Shoes shoes) {
        return Review.builder()
                .user(user)
                .shoes(shoes)
                .content(content)
                .rating(rating)
                .feeling(feeling)
                .fit(fit)
                .width(width)
                .build();
    }
}
