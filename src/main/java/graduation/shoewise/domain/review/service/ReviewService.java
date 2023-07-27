package graduation.shoewise.domain.review.service;

import graduation.shoewise.domain.review.dto.*;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    public Long save(Long userId, Long shoesId, ReviewSaveRequestDto requestDto/*, MultipartFile multipartFile*/) throws BaseException {

        String image = null;

        /*if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "static");
        }*/

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(INVALID_USER_ID));

        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발 없음"));

        log.info("리뷰 등록 시 사용자 닉네임 : " + user.getNickname());

        if(reviewRepository.findByUserIdAndShoes(user.getId(), shoes.getId()).isEmpty()){
            Review review = reviewRepository.save(requestDto.toEntity(user, shoes));
            shoesRepository.updateShoesStatisticsForReviewInsert(shoes.getId(), requestDto.getRating());

            log.info("신발 평점 : " + shoes.getAvgRating());

            return new ReviewResponseDto(review).getId();

        }else{
            throw new IllegalArgumentException("이미 리뷰를 작성했습니다");
        }
    }

    // 리뷰 수정
    @Transactional
    public Long update(Long id, Long userId, ReviewUpdateRequestDto requestDto) throws BaseException {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        if(!review.isWrittenBy(user)){
            System.out.println("유저 접근 금지");
            throw new IllegalArgumentException("해당 유저가 아닙니당");
        }

        double preRate = review.getRating();

        log.info("PreRate : " + preRate);

        review.updateReview(requestDto);
        double updateRate = review.getRating();
        log.info("updateRate : " + updateRate);

        double ratingGap = updateRate-preRate;

        log.info("RatingGap : " + ratingGap);
        shoesRepository.updateShoesStatisticsForReviewUpdate(review.getShoes().getId(), ratingGap);

        return reviewRepository.save(requestDto.toEntity()).getId();
    }

    // 리뷰 상세 조회
    public ReviewResponseDto findById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. id=" + id));

        return new ReviewResponseDto(review);
    }

    // 특정 신발 리뷰 조회
    public ReviewWithUserPageResponseDto findAllByShoesId(Long shoesId, Long userId,
                                                           Pageable pageable) throws BaseException {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발이 없습니다. id =" + shoesId));

        final Slice<Review> reviews = reviewRepository.findAllByShoesId(shoesId, pageable);

        if (userId == null) {
            return ReviewWithUserPageResponseDto.from(reviews);
        }
        return ReviewWithUserPageResponseDto.of(reviews, userId);
    }

    // 유저가 작성한 리뷰 조회
    public ReviewWithShoesPageResponseDto findReviewsByUserId(Long userId,
                                                              Pageable pageable) throws BaseException{
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        log.info("user : " + user.getNickname());

        Slice<Review> page = reviewRepository.findReviewByUserId(userId, pageable);

        return ReviewWithShoesPageResponseDto.from(page);
    }

    // 리뷰 삭제
    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. reviewId=" + reviewId));

        reviewRepository.deleteById(reviewId);

        shoesRepository.updateShoesStatisticsForReviewDelete(review.getShoes().getId(), review.getRating());
    }
}
