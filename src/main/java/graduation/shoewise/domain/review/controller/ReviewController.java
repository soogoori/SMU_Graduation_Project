package graduation.shoewise.domain.review.controller;

import graduation.shoewise.domain.review.dto.*;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.review.service.ReviewService;
import graduation.shoewise.global.security.oauth.service.SecurityUtil;
import graduation.shoewise.global.security.oauth.service.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @ApiOperation(value = "post review", notes = "리뷰 등록")
    @PostMapping(value = "/shoes/{shoesId}/reviews")
    public void post(@PathVariable Long shoesId,
                     @RequestBody ReviewSaveRequestDto requestDto/*,
                     @RequestPart(value = "image", required = false) MultipartFile multipartFile*/) throws BaseException, IOException {

        Long userId = SecurityUtil.getCurrentMemberPk();

        reviewService.save(userId, shoesId, requestDto/*, multipartFile*/);
    }

    // 사용자가 작성한 리뷰 조회
    @ApiOperation(value = "view user's review", notes="사용자가 작성한 리뷰 조회")
    @GetMapping("/users/{userId}/reviews")
    public ReviewWithShoesPageResponseDto getReviewsByUserId(@PathVariable Long userId,
                                                       Pageable pageable) throws BaseException {
        return reviewService.findReviewsByUserId(userId, pageable);
    }


    // 특정 신발 리뷰 조회
    @ApiOperation(value = "view shoes' review", notes="특정 신발 리뷰 조회")
    @GetMapping("/shoes/{shoesId}/reviews")
    public ReviewWithUserPageResponseDto findAllByShoesId(@PathVariable Long shoesId,
                                                          Pageable pageable) throws BaseException {

        Long userId = SecurityUtil.getCurrentMemberPk();
        //Long userId = 2L;
        return reviewService.findAllByShoesId(shoesId, userId, pageable);
    }

    // AI 리뷰 가져오기
    @ApiOperation(value = "Get shoes' AI review", notes="AI 리뷰 불러오기")
    @GetMapping("/shoes/{shoesId}/aiReviews")
    public AIReviewResponseDto getAIReview(@PathVariable Long shoesId) throws IOException {

        String path = "/Users/soobin/Desktop/shoename_content.csv";

        return reviewService.getAIReviewByShoesName(shoesId, path);
    }

    // AI 뉴스 가져오기
    @ApiOperation(value = "Get shoes' AI News", notes="AI 뉴스 불러오기")
    @GetMapping("/shoes/{shoesId}/aiNews")
    public AINewsResponseDto getAINews(@PathVariable Long shoesId) throws IOException {

        String path = "/Users/soobin/Desktop/chatgpt_shoereview.csv";

        return reviewService.getAINewsByShoesName(shoesId, path);
    }


    // 리뷰 수정
    @ApiOperation(value = "update shoes' review", notes="특정 신발 리뷰 수정")
    @PutMapping("/{shoesId}/reviews/{id}")
    public void updateReview(@PathVariable Long shoesId,
                             @PathVariable Long id,
                             @RequestBody ReviewUpdateRequestDto requestDto
                             /*@RequestPart(value = "image" ,required = false) MultipartFile multipartFile*/) throws BaseException, IOException {

        Long userId = SecurityUtil.getCurrentMemberPk();
        reviewService.update(shoesId, id, userId, requestDto/*, multipartFile*/);
    }


    // 리뷰 삭제
    @ApiOperation(value = "delete shoes' review", notes="특정 신발 리뷰 삭제")
    @DeleteMapping("/{shoesId}/reviews/{id}")
    public void deleteReview(@PathVariable Long shoesId, @PathVariable Long id) throws BaseException, IOException {
        Long userId = SecurityUtil.getCurrentMemberPk();
        reviewService.delete(id, userId);
    }
}
