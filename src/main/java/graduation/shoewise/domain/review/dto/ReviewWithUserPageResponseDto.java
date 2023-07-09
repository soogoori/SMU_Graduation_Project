package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.Review;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewWithUserPageResponseDto {

    private final boolean hasNext;
    private final List<ReviewWithUserResponse> reviews;

    public ReviewWithUserPageResponseDto(boolean hasNext, List<ReviewWithUserResponse> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }

    public static ReviewWithUserPageResponseDto of(Slice<Review> slice, Long userId) {
        List<ReviewWithUserResponse> reviews = slice.getContent()
                .stream()
                .map(review -> ReviewWithUserResponse.of(review, userId))
                .collect(Collectors.toList());

        return new ReviewWithUserPageResponseDto(slice.hasNext(), reviews);
    }
    public static ReviewWithUserPageResponseDto from(Slice<Review> slice) {
        List<ReviewWithUserResponse> reviews = slice.getContent()
                .stream()
                .map(ReviewWithUserResponse::from)
                .collect(Collectors.toList());

        return new ReviewWithUserPageResponseDto(slice.hasNext(), reviews);
    }
}
