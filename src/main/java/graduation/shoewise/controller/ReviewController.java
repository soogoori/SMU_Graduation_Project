package graduation.shoewise.controller;

import graduation.shoewise.config.BaseException;
import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import graduation.shoewise.entity.review.dto.ReviewSaveRequestDto;
import graduation.shoewise.entity.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    /*@PostMapping("/api/reviews")
    public Long save(@RequestBody ReviewSaveRequestDto requestDto) {
        return reviewService.save(requestDto);
    }*/

    // 특정 리뷰 조회
    @ApiOperation(value = "view review", notes="특정 리뷰 조회")
    @GetMapping("/api/reviews/{id}")
    public ReviewResponseDto findById(@PathVariable Long id) throws BaseException {
        return reviewService.findById(id);
    }

    // 사용자가 작성한 리뷰 조회
    @GetMapping("/api/reviews")
    public ReviewResponseDto findByUser(@RequestParam("userId") Long userId) throws BaseException  {

        return reviewService.findByUser(userId);
    }

    // 특정 신발 리뷰 조회
    @GetMapping("/api/{shoesId}/reviews")
    public ReviewResponseDto findByShoesId(@PathVariable Long shoesId) throws BaseException {
        return reviewService.findByShoes(shoesId);
    }


    // 리뷰 수정
    @PutMapping("/api/{shoesId}/reviews/{id}")
    public Long updateReview(@PathVariable Long shoesId, @PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto) throws BaseException {
        return reviewService.update(id, requestDto);
    }

    // 리뷰 등록
    @PostMapping("/api/{shoesId}/reviews/{userId}")
    public ReviewResponseDto postReview(@PathVariable Long shoesId, @PathVariable Long userId, @RequestBody ReviewSaveRequestDto requestDto) throws BaseException {
        return reviewService.save(userId, shoesId, requestDto);
    }

    // 리뷰 삭제
    @DeleteMapping("/api/{shoesId}//reviews/{id}")
    public void deleteReview(@PathVariable Long shoesId, @PathVariable Long id){
        reviewService.delete(id);
    }
}
