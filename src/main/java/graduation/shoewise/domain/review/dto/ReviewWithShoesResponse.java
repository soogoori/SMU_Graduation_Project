package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.enums.Feeling;
import graduation.shoewise.domain.enums.Fit;
import graduation.shoewise.domain.enums.Width;
import graduation.shoewise.domain.review.Review;
import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewWithShoesResponse {

    private Long id;
    private ShoesResponseDto shoes;
    private String content;
    private final Integer rating;
    private final Feeling feeling;
    private final Fit fit;
    private final Width width;

    public static ReviewWithShoesResponse from(Review review){
        return new ReviewWithShoesResponse(review.getId(), ShoesResponseDto.from(review.getShoes()),
                review.getContent(), review.getRating(), review.getFeeling(), review.getFit(), review.getWidth());
    }
}
