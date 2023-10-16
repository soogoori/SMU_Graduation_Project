package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String profileImage;
    private final String content;
    private final String nickname;
    private final double rating;
    private final Feeling feeling;
    private final Fit fit;
    private final Width width;
    private final int size;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.profileImage = entity.getUser().getProfileImage();
        this.nickname = entity.getUser().getNickname();
        this.rating=entity.getRating();
        this.fit = entity.getFit();
        this.width = entity.getWidth();
        this.feeling = entity.getFeeling();
        this.size = entity.getSize();
    }
}
