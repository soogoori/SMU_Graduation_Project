package graduation.shoewise.domain.shoes.controller;

import graduation.shoewise.domain.shoes.dto.*;
import graduation.shoewise.domain.shoes.service.ShoesService;
import graduation.shoewise.domain.user.dto.UserDto;
import graduation.shoewise.global.security.oauth.service.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/shoes")
public class ShoesAdminController {

    private final ShoesService shoesService;

    /**
     * 관리자만 가능
     */

    // 신발 게시물 등록
    @PostMapping
    @ApiOperation(value = "post shoes", notes = "신발 게시물 등록")
    public ResponseEntity<ShoesRegisterResponseDto> post(@RequestBody ShoesRegisterRequestDto requestDto) {

        log.info("requestDto : " + requestDto.getName());
        log.info("requestDto : " + requestDto.getBrand());
        log.info("requestDto : " + requestDto.getImage());

        Long saveId = shoesService.registerShoes(requestDto);

        return ResponseEntity.created(URI.create("/api/products/" + saveId))
                .build();
    }

    // 신발 게시물 수정
    @PutMapping("/{shoesId}")
    @ApiOperation(value = "update shoes", notes = "신발 게시물 수정")
    public void update(@PathVariable Long shoesId,
                       @RequestBody ShoesUpdateRequestDto requestDto) {
        shoesService.update(shoesId, requestDto);
    }


    // 신발 게시물 삭제
    @DeleteMapping("/{shoesId}")
    @ApiOperation(value = "delete shoes", notes = "신발 게시물 삭제")

    public void delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long shoesId) {
        shoesService.deleteShoes(shoesId, userPrincipal.getId());
    }



}
