package graduation.shoewise.service;

import graduation.shoewise.config.BaseException;
import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import graduation.shoewise.entity.review.dto.ReviewSaveRequestDto;
import graduation.shoewise.entity.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.entity.review.Review;
import graduation.shoewise.entity.shoes.Shoes;
import graduation.shoewise.entity.user.User;
import graduation.shoewise.repository.ReviewRepository;
import graduation.shoewise.repository.ShoesRepository;
import graduation.shoewise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static graduation.shoewise.config.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShoesRepository shoesRepository;

    // 리뷰 등록
    @Transactional
    public ReviewResponseDto save(Long userId, Long shoesId, ReviewSaveRequestDto requestDto) throws BaseException {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(INVALID_USER_ID));

        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발 없음"));

        log.info("리뷰 등록 시 사용자 닉네임 : " + user.getNickname());

        requestDto.setNickname(user.getNickname());

        Review review = reviewRepository.save(requestDto.toEntity(user, shoes));

        return new ReviewResponseDto(review);
    }

    // 리뷰 수정
    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto) throws BaseException {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        review.modifyReview(requestDto.getContent(), requestDto.getFit(), requestDto.getWidth(),
                requestDto.getFeeling(), requestDto.getRating());

        return reviewRepository.save(requestDto.toEntity()).getId();
    }

    // 리뷰 상세 조회
    public ReviewResponseDto findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        return new ReviewResponseDto(review);
    }

    // 특정 신발 리뷰 조회
    public ReviewResponseDto findByShoes(Long shoesId) throws BaseException {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발이 없습니다. id =" + shoesId));

        Review review = reviewRepository.findByShoes(shoes)
                        .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        return new ReviewResponseDto(review);
    }

    // 유저가 작성한 리뷰 조회
    public ReviewResponseDto findByUser(Long userId) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        log.info("user : " + user.getNickname());

        Review review = reviewRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다."));

        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. reviewId=" + reviewId));
        reviewRepository.deleteById(reviewId);
    }

}
