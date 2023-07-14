package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Feeling;
import graduation.shoewise.domain.review.entity.Fit;
import graduation.shoewise.domain.review.entity.Width;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewWithShoesResponseDto {

    private Long id;
    private ShoesResponseDto shoes;
    private String content;
    private final Integer rating;
    private final Feeling feeling;
    private final Fit fit;
    private final Width width;

    public static ReviewWithShoesResponseDto from(Review review){
        return new ReviewWithShoesResponseDto(review.getId(), ShoesResponseDto.from(review.getShoes()),
                review.getContent(), review.getRating(), review.getFeeling(), review.getFit(), review.getWidth());
    }
}
