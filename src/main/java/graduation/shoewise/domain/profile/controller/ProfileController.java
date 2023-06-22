package graduation.shoewise.domain.profile.controller;

import graduation.shoewise.domain.profile.dto.ProfileRequestDto;
import graduation.shoewise.domain.profile.dto.ProfileResponseDto;
import graduation.shoewise.domain.profile.service.ProfileService;
import graduation.shoewise.domain.purchase.dto.PurchaseResponseDto;
import graduation.shoewise.domain.review.dto.ReviewResponseDto;
import graduation.shoewise.domain.user.dto.UserDto;
import graduation.shoewise.domain.user.service.UserService;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.global.security.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    // 프로필 수정
    @ApiOperation(value = "update my profile info", notes = "내 정보 수정하기 - 닉네임, 발사이즈, 발폭 등")
    @PatchMapping("/me")
    public ResponseEntity<ProfileResponseDto> updateProfile(@AuthenticationPrincipal UserDto userDto,
                                                            @RequestPart ProfileRequestDto requestDto,
                                                            @RequestPart(value = "image", required = false)MultipartFile multipartFile) throws BaseException {

        return ResponseEntity.ok().body(profileService.updateProfile(userDto.getId(), requestDto, multipartFile));
    }

    //프로필 조회 - 내가 작성한 리뷰
    @ApiOperation(value = "view user review", notes = "내가 작성한 리뷰 조회하기")
    @GetMapping("/me/reviews")
    public Page<ReviewResponseDto> getMyReview(@AuthenticationPrincipal UserDto userDto,
                                               @PageableDefault(size = 5) Pageable pageable) throws Exception {

        return profileService.getMyReview(userDto.getId(), pageable);
    }

    //프로필 조회 - 내가 구매한 목록
    @ApiOperation(value = "view user purchase list", notes = "내가 구매한 목록 조회하기")
    @GetMapping("/me/purchases")
    public Page<PurchaseResponseDto> getMyPurchase(@AuthenticationPrincipal UserDto userDto,
                                                   @PageableDefault(size = 5) Pageable pageable) throws Exception {

        return profileService.getMyPurchase(userDto.getId(), pageable);
    }


    // 미니 프로필 조회 -> 리뷰 작성한 유저의 닉네임에 마우스 갖다대면 구매목록 조회 가능


    // 닉네임 중복 여부
    @ApiOperation(value = "check nickname duplicate", notes = "닉네임 중복확인하기")
    @GetMapping("/user/{nickname}/exists")

    public ResponseEntity<Boolean> checkNickNameDuplicate(@PathVariable String nickname) {

        return ResponseEntity.ok(userService.checkNickNameDuplicate(nickname));
    }
}
