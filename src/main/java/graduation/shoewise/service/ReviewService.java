package graduation.shoewise.service;

import graduation.shoewise.config.BaseException;
import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import graduation.shoewise.entity.review.dto.ReviewSaveRequestDto;
import graduation.shoewise.entity.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.user.User;
import graduation.shoewise.repository.ReviewRepository;
import graduation.shoewise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static graduation.shoewise.config.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 리뷰 등록
    @Transactional
    public Long save(Long userId, ReviewSaveRequestDto requestDto) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(INVALID_USER_ID));

        requestDto.setNickname(user.getNickname());

        return reviewRepository.save(requestDto.toEntity()).getId();
    }

    // 리뷰 수정
    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        review.update(requestDto.getContent());

        return id;
    }

    // 리뷰 조회
    public ReviewResponseDto findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        return new ReviewResponseDto(review);
    }


}
