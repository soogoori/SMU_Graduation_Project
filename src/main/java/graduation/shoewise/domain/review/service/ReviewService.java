package graduation.shoewise.domain.review.service;

import graduation.shoewise.domain.review.dto.*;
import graduation.shoewise.global.S3Uploader;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.review.entity.Review;
import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.review.repository.ReviewRepository;
import graduation.shoewise.domain.shoes.repository.ShoesRepository;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static graduation.shoewise.global.config.BaseResponseStatus.INVALID_USER_ID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ShoesRepository shoesRepository;
    private final S3Uploader s3Uploader;


    // 리뷰 등록
    @Transactional
    public Long save(Long userId, Long shoesId, ReviewSaveRequestDto requestDto/*, MultipartFile multipartFile*/) throws BaseException, IOException {

        String image = null;

       /* if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "static");
        }*/

        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(INVALID_USER_ID));

        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발 없음"));

        log.info("리뷰 등록 시 사용자 닉네임 : " + user.getNickname());

        if(reviewRepository.findByUserIdAndShoes(user.getId(), shoes.getId()).isEmpty()){
            Review review = reviewRepository.save(requestDto.toEntity(user, shoes, image));
            shoesRepository.updateShoesStatisticsForReviewInsert(shoes.getId(), requestDto.getRating());

            log.info("신발 평점 : " + shoes.getAvgRating());

            return new ReviewResponseDto(review).getId();

        }else{
            throw new IllegalArgumentException("이미 리뷰를 작성했습니다");
        }
    }

    // 리뷰 수정
    @Transactional
    public Long update(Long id, Long userId, ReviewUpdateRequestDto requestDto, MultipartFile multipartFile) throws BaseException, IOException {

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

        String image = null;
        if (!multipartFile.isEmpty()) { // 사진이 수정된 경우
            image = (s3Uploader.upload(multipartFile, "review"));// 새로들어온 이미지 s3 저장
            Review reviews = reviewRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다.")
            );
            if (!review.getImage().equals("")) {
                s3Uploader.delete(review.getImage(), "review");  // 이전 이미지 파일 삭제
            }
            reviews.update(image);
        }
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

    // AI 리뷰 조회
    public AIReviewResponseDto getAIReviewByShoesName(Long shoesId, String filePath) throws IOException {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 신발이 없습니다. id =" + shoesId));

        String name = shoes.getName();
        List<String> aiReview = new ArrayList<>();
        String shoesName="";

        String path = "/Users/soobin/Desktop/shoe_review.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (Reader reader = Files.newBufferedReader(Paths.get(filePath),StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            for (CSVRecord record : csvParser) {
                if (record.size() >= 2) {
                    log.info("레코드 0번에 뭐있냐 " + record.get(0).substring(1));
                    log.info("이름 "+ name.length());

                    String getRecord = record.get(0).substring(1);
                    log.info("이름 "+ getRecord.length());
                    if (name.equals(getRecord)) {
                        log.info("이름같음 "+ name);
                        String[] contentArray = record.get(1).split("\\n");
                        log.info("레코드 1번에 뭐있냐 " + record.get(1));
                        aiReview = Arrays.asList(contentArray);
                        shoesName = name;
                        log.info("신발이름" + shoesName);
                        break;  // 찾았으면 반복 중단
                    }
                }
            }
        } catch (IOException e) {
            // 파일 읽기 예외 처리
            log.error("파일 읽기 중 오류 발생: " + e.getMessage());
            throw e;
        }
        return new AIReviewResponseDto(shoesId, shoesName,aiReview);
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
    public void delete(Long reviewId, Long userId) throws BaseException, IOException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 없습니다. reviewId=" + reviewId));

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new BaseException(INVALID_USER_ID));

        if(!review.isWrittenBy(user)){
            System.out.println("유저 접근 금지");
            throw new IllegalArgumentException("해당 유저가 아닙니당");
        }
        shoesRepository.updateShoesStatisticsForReviewDelete(review.getShoes().getId(), review.getRating());
        reviewRepository.deleteById(reviewId);

        log.info("updateShoesStatisticsForReviewDelete 진입시도");
        log.info("해당 신발 리뷰 수 변경전: " + review.getShoes().getReviewCount());

        log.info("updateShoesStatisticsForReviewDelete 진입완료");
        log.info("해당 신발 리뷰 수 변경 후: " + review.getShoes().getReviewCount());

    }
}
