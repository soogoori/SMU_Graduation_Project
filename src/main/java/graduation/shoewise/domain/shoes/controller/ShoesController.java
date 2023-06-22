package graduation.shoewise.domain.shoes.controller;

import graduation.shoewise.domain.shoes.dto.ShoesResponseDto;
import graduation.shoewise.domain.shoes.service.ShoesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
