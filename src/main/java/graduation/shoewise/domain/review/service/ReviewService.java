package graduation.shoewise.domain.review.service;

import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.review.dto.ReviewResponseDto;
import graduation.shoewise.domain.review.dto.ReviewSaveRequestDto;
import graduation.shoewise.domain.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.domain.review.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static graduation.shoewise.global.config.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShoesRepository shoesRepository;

    // 리뷰 등록
    @Transactional
    public Long save(Long userId, Long shoesId, ReviewSaveRequestDto requestDto, MultipartFile multipartFile) throws BaseException {

        String image = null;

        /*if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "static");

        }*/

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(INVALID_USER_ID));

        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발 없음"));

        log.info("리뷰 등록 시 사용자 닉네임 : " + user.getNickname());

        Review review = reviewRepository.save(requestDto.toEntity(user, shoes));

        return new ReviewResponseDto(review).getId();
    }

    // 리뷰 수정
    @Transactional
    public Long update(Long id, ReviewUpdateRequestDto requestDto) throws BaseException {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        review.updateReview(requestDto);

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
    public ReviewResponseDto findByUser(Long userId,
                                        Pageable pageable) throws BaseException{
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
