package graduation.shoewise.domain.shoes.controller;

import graduation.shoewise.domain.shoes.dto.*;
import graduation.shoewise.domain.shoes.service.ShoesService;
import graduation.shoewise.domain.user.dto.UserDto;
import graduation.shoewise.global.security.oauth.service.SecurityUtil;
import graduation.shoewise.global.security.oauth.service.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<ShoesRegisterResponseDto> post(@RequestPart ShoesRegisterRequestDto requestDto,
                                                         @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException{

        log.info("requestDto : " + requestDto.getName());
        log.info("requestDto : " + requestDto.getBrand());
        log.info(multipartFile.getName());
        //log.info("requestDto : " + requestDto.getImage());

        Long saveId = shoesService.registerShoes(requestDto, multipartFile);

        return ResponseEntity.created(URI.create("/api/products/" + saveId))
                .build();
    }

    // 신발 게시물 수정
    @PutMapping("/{shoesId}")
    @ApiOperation(value = "update shoes", notes = "신발 게시물 수정")
    public void update(@PathVariable Long shoesId,
                       @RequestPart ShoesUpdateRequestDto requestDto,
                       @RequestPart (value = "image" ,required = false) MultipartFile multipartFile) throws IOException {
        shoesService.update(shoesId, requestDto,multipartFile);
    }


    // 신발 게시물 삭제
    @DeleteMapping("/{shoesId}")
    @ApiOperation(value = "delete shoes", notes = "신발 게시물 삭제")

    public void delete(@PathVariable Long shoesId) {

        Long userId = SecurityUtil.getCurrentMemberPk();
        shoesService.deleteShoes(shoesId, userId);
    }



}
