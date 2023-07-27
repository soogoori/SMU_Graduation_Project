package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReviewSaveRequestDto {

    @NotNull(message = "리뷰 내용이 없습니다")
    private String content;
    private String image;

    @NotNull(message = "평가 점수가 없습니다")
    private double rating;

    @NotNull(message = "착화감을 알려주세요")
    private Feeling feeling;

    @NotNull(message = "신발이 잘 맞는지 알려주세요")
    private Fit fit;

    @NotNull(message = "발볼이 잘 맞는지 알려주세요")
    private Width width;

    public Review toEntity(User user, Shoes shoes) {
        return Review.builder()
                .user(user)
                .shoes(shoes)
                .image(this.image)
                .content(this.content)
                .rating(this.rating)
                .feeling(this.feeling)
                .fit(this.fit)
                .width(this.width)
                .build();
    }
}
