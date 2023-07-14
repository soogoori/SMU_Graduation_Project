package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.user.User;

public class ReviewWithUserResponseDto {

    private Long id;
    private ReviewAuthorResponseDto author;
    private Long shoesId;
    private String content;
    private int rating;
    private Fit fit;
    private Width width;
    private Feeling feeling;
    private boolean isUser;

    public ReviewWithUserResponseDto(final Long id, final ReviewAuthorResponseDto author,
                                     final Long shoesId, final String content, final int rating,
                                     final Fit fit, final Width width, final Feeling feeling, final boolean isUser) {
        this.id = id;
        this.author = author;
        this.shoesId = shoesId;
        this.content = content;
        this.rating = rating;
        this.fit = fit;
        this.width = width;
        this.feeling = feeling;
        this.isUser = isUser;
    }

    public static ReviewWithUserResponseDto of(final Review review, final Long userId) {
        final User user = review.getUser();
        final ReviewAuthorResponseDto authorResponse = ReviewAuthorResponseDto.from(user);
        return new ReviewWithUserResponseDto(review.getId(), authorResponse, review.getShoes().getId(),
                review.getContent(), review.getRating(), review.getFit(), review.getWidth(),
                review.getFeeling(), user.isSameId(userId));

    }

    public static ReviewWithUserResponseDto from(final Review review) {
        final User user = review.getUser();
        final ReviewAuthorResponseDto authorResponse = ReviewAuthorResponseDto.from(user);
        return new ReviewWithUserResponseDto(review.getId(), authorResponse, review.getShoes().getId(),
                review.getContent(), review.getRating(), review.getFit(), review.getWidth(),
                review.getFeeling(), false);

    }
}
