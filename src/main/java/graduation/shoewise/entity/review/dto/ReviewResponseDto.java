package graduation.shoewise.entity.review.dto;

import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.enums.Feeling;
import graduation.shoewise.entity.enums.Fit;
import graduation.shoewise.entity.enums.Width;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private final Long id;
    private final String content;
    private final String nickname;
    private final Integer rating;
    private final Feeling feeling;
    private final Fit fit;
    private final Width width;


    public ReviewResponseDto(Review entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        //this.nickname = entity.getUser().getName();
        this.nickname = entity.getUser().getNickname();
        this.rating=entity.getRating();
        this.fit = entity.getFit();
        this.width = entity.getWidth();
        this.feeling = entity.getFeeling();
    }
}
