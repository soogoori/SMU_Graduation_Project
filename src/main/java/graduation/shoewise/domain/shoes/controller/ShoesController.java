package graduation.shoewise.domain.shoes.controller;

import graduation.shoewise.domain.shoes.Shoes;
import graduation.shoewise.domain.shoes.dto.ShoesPageResponse;
import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import graduation.shoewise.domain.shoes.service.ShoesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shoes")
public class ShoesController {

    private final ShoesService shoesService;

    // 신발 게시물 조회
    @GetMapping("/{shoesId}")
    @ApiOperation(value = "get shoes", notes = "신발 게시물 조회")
    public ShoesResponseDto getShoes(@PathVariable Long shoesId) {
        return shoesService.getShoesById(shoesId);
    }

    // 신발 게시물 전체 조회
    @GetMapping
    @ApiOperation(value = "get all shoes", notes = "신발 게시물 전체 조회")
    public ShoesPageResponse getAllShoes(final Pageable pageable) {
        return shoesService.getShoesAll(pageable);
    }

    @GetMapping("/category")
    @ApiOperation(value = "get shoes by category", notes = "신발 카테고리별 게시물 전체 조회")
    public ShoesPageResponse getShoesByCategory(final Pageable pageable, @RequestParam String brand){
        log.info("brand : " + brand);
        return shoesService.getShoesByCategory(pageable, brand);
    }
}
