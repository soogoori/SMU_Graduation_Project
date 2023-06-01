package graduation.shoewise.controller;

import graduation.shoewise.entity.review.dto.ReviewResponseDto;
import graduation.shoewise.entity.review.dto.ReviewSaveRequestDto;
import graduation.shoewise.entity.review.dto.ReviewUpdateRequestDto;
import graduation.shoewise.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public Long save(@RequestBody ReviewSaveRequestDto requestDto) {
        return reviewService.save(requestDto);
    }

    // 개별 조회
    @GetMapping("/api/reviews/{id}")
    public ReviewResponseDto findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }


    // 게시물 수정
    @PutMapping("/api/reviews/{id}")
    public Long update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto requestDto) {
        return reviewService.update(id, requestDto);
    }
}
