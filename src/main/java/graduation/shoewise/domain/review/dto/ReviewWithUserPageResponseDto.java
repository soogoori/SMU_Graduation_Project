package graduation.shoewise.domain.review.dto;

import graduation.shoewise.domain.review.entity.Review;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewWithUserPageResponseDto {

    private final boolean hasNext;
    private final List<ReviewWithUserResponseDto> reviews;

    public ReviewWithUserPageResponseDto(boolean hasNext, List<ReviewWithUserResponseDto> reviews) {
        this.hasNext = hasNext;
        this.reviews = reviews;
    }

    public static ReviewWithUserPageResponseDto of(Slice<Review> slice, Long userId) {
        List<ReviewWithUserResponseDto> reviews = slice.getContent()
                .stream()
                .map(review -> ReviewWithUserResponseDto.of(review, userId))
                .collect(Collectors.toList());

        return new ReviewWithUserPageResponseDto(slice.hasNext(), reviews);
    }

    //Stream의 요소들을 ReviewWithUserResponseDto from 으로 변환하여, 그 결과를 List로 반환받기
    public static ReviewWithUserPageResponseDto from(Slice<Review> slice) {
        List<ReviewWithUserResponseDto> reviews = slice.getContent()
                .stream()
                .map(ReviewWithUserResponseDto::from)
                .collect(Collectors.toList());

        return new ReviewWithUserPageResponseDto(slice.hasNext(), reviews);
    }
}
