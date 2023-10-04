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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

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
                     @RequestPart ReviewSaveRequestDto requestDto,
                     @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws BaseException, IOException {

        Long userId = SecurityUtil.getCurrentMemberPk();

        reviewService.save(userId, shoesId, requestDto, multipartFile);
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


    // 리뷰 수정
    @ApiOperation(value = "update shoes' review", notes="특정 신발 리뷰 수정")
    @PutMapping("/{shoesId}/reviews/{id}")
    public Long updateReview(@PathVariable Long shoesId,
                             @PathVariable Long id,
                             @RequestPart ReviewUpdateRequestDto requestDto,
                             @RequestPart(value = "image" ,required = false) MultipartFile multipartFile) throws BaseException, IOException {

        Long userId = SecurityUtil.getCurrentMemberPk();
        return reviewService.update(id, userId, requestDto, multipartFile);
    }


    // 리뷰 삭제
    @ApiOperation(value = "delete shoes' review", notes="특정 신발 리뷰 삭제")
    @DeleteMapping("/api/{shoesId}/reviews/{id}")
    public void deleteReview(@PathVariable Long shoesId, @PathVariable Long id) throws BaseException, IOException {
        Long userId = SecurityUtil.getCurrentMemberPk();
        reviewService.delete(id, userId);
    }
}
