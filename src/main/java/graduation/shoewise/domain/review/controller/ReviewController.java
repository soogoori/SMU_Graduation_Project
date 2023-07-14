package graduation.shoewise.domain.review.controller;

import graduation.shoewise.domain.review.dto.*;
import graduation.shoewise.domain.user.dto.UserDto;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.review.service.ReviewService;
import graduation.shoewise.global.security.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    /*@PostMapping("/api/reviews")
    public Long save(@RequestBody ReviewSaveRequestDto requestDto) {
        return reviewService.save(requestDto);
    }*/

    // 리뷰 등록
    @ApiOperation(value = "post review", notes = "리뷰 등록")
    @PostMapping("/shoes/{shoesId}/reviews/")
    public ResponseEntity<ReviewResponseDto> post(@PathVariable Long shoesId,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @RequestBody ReviewSaveRequestDto requestDto,
                                                  @RequestPart(value = "image", required = false) MultipartFile multipartFile
    ) throws BaseException {

        Long id = reviewService.save(userPrincipal.getId(), shoesId, requestDto, multipartFile);
        return ResponseEntity.created(URI.create("/api/reviews/" + id))
                .build();
    }

    // 사용자가 작성한 리뷰 조회
    @ApiOperation(value = "view user's review", notes="사용자가 작성한 리뷰 조회")
    @GetMapping("/users/{userId}/reviews")
    public ReviewWithShoesPageResponseDto getReviewsByUserId(@PathVariable Long userId,
                                                       Pageable pageable) throws BaseException  {

        return reviewService.findReviewsByUserId(userId, pageable);
    }


    // 특정 신발 리뷰 조회
    @ApiOperation(value = "view shoes' review", notes="특정 신발 리뷰 조회")
    @GetMapping("/shoes/{shoesId}/reviews")
    public ReviewWithUserPageResponseDto findAllByShoesId(@PathVariable Long shoesId,
                                                          @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                          Pageable pageable) throws BaseException {
        return reviewService.findAllByShoesId(shoesId, userPrincipal.getId(), pageable);
    }


    // 리뷰 수정
    @PutMapping("/{shoesId}/reviews/{id}")
    public Long updateReview(@AuthenticationPrincipal UserPrincipal userPrincipal,
                             @PathVariable Long shoesId,
                             @PathVariable Long id,
                             @RequestBody ReviewUpdateRequestDto requestDto) throws BaseException {


        return reviewService.update(id, requestDto);
    }


    // 리뷰 삭제
    @DeleteMapping("/api/{shoesId}/reviews/{id}")
    public void deleteReview(@PathVariable Long shoesId, @PathVariable Long id){
        reviewService.delete(id);
    }
}
