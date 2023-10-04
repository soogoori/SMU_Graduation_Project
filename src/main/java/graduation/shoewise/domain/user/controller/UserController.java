package graduation.shoewise.domain.user.controller;

import graduation.shoewise.domain.user.dto.UserRequestDto;
import graduation.shoewise.domain.user.dto.UserResponseDto;
import graduation.shoewise.global.config.BaseException;
import graduation.shoewise.domain.user.service.UserService;
import graduation.shoewise.global.security.oauth.service.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 프론트에서 인가코드 받아오는 url
    /*@GetMapping("/oauth/token")
    public OAuthToken login(@RequestParam("code") String code){
        // 넘어온 인가 코드를 통해 access_token 발급
        OAuthToken oauthToken = userService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String user = userService.saveUser(oauthToken);

        return oauthToken;
    }*/

    // 프로필 수정 -> ProfileController에 있는 것과 이거 중에 선택하기.......
    @ApiOperation(value = "update my info", notes = "내 정보 수정하기 - 닉네임, 발사이즈, 발폭 등")
    @PutMapping("/me")
    public ResponseEntity<Void> updateMe (@RequestBody UserRequestDto requestDto) throws BaseException {

        Long userId = SecurityUtil.getCurrentMemberPk();

        userService.updateUser(userId, requestDto);

        return ResponseEntity.ok().build();
    }

    //사용자 프로필 조회
    @ApiOperation(value = "view user info", notes = "유저 프로필 조회하기")
    @GetMapping("/{userId}")
    public UserResponseDto viewUser(@PathVariable Long userId) throws Exception {

        Long memberId = SecurityUtil.getCurrentMemberPk();

        return userService.getUserInfo(userId, memberId);
    }

    //내 프로필 조회
    @ApiOperation(value = "view my info", notes = "내 프로필 조회하기")
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> viewMyInfo() throws Exception {

        Long userId = SecurityUtil.getCurrentMemberPk();
        UserResponseDto userResponseDto = userService.getMyInfo(userId);
        return ResponseEntity.ok().body(userResponseDto);
    }

    // 미니 프로필 조회 -> 리뷰 작성한 유저의 닉네임에 마우스 갖다대면 구매목록 조회 가능


    // 닉네임 중복 여부
    @ApiOperation(value = "check nickname duplicate", notes = "닉네임 중복확인하기")
    @GetMapping("/{nickname}/exists")
    public ResponseEntity<Boolean> checkNickNameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.checkNickNameDuplicate(nickname));
    }
}
