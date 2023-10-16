package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ReviewRequestDto {


    @NotNull(message = "리뷰 내용이 없습니다.")
    private String content;

    private String image;

    @NotNull(message = "리뷰 점수가 없습니다.")
    private double rating;

    @NotNull(message = "착화감 정보가 없습니다.")
    private Feeling feeling;

    @NotNull(message = "크기 정보가 없습니다.")
    private Fit fit;

    @NotNull(message = "발볼 정보가 없습니다.")
    private Width width;

    @NotNull(message = "신발 사이즈 정보가 없습니다.")
    private int size;

    public Review toEntity(User user, Shoes shoes) {
        return Review.builder()
                .user(user)
                .shoes(shoes)
                .image(image)
                .content(content)
                .rating(rating)
                .feeling(feeling)
                .fit(fit)
                .width(width)
                .size(size)
                .build();
    }
}
