package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewWithShoesPageResponseDto {

    private final boolean hasNext;
    private final List<ReviewWithShoesResponseDto> reviews;

    public ReviewWithShoesPageResponseDto(boolean hasNext, List<ReviewWithShoesResponseDto> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }

    public static ReviewWithShoesPageResponseDto from(Slice<Review> page) {
        final List<ReviewWithShoesResponseDto> reviews = page.getContent().stream()
                .map(ReviewWithShoesResponseDto::from)
                .collect(Collectors.toList());
        return new ReviewWithShoesPageResponseDto(page.hasNext(),reviews);
    }
}
