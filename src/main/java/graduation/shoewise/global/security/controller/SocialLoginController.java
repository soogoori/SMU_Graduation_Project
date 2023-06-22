package graduation.shoewise.global.security.controller;

import graduation.shoewise.global.security.jwt.AuthTokenProvider;
import graduation.shoewise.global.security.oauth.token.AuthToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login/oauth2")
@Slf4j
public class SocialLoginController {

    private final AuthTokenProvider tokenProvider;
    private final AuthTokenProvider authTokenProvider;

    /**
     * KAKAO 소셜 로그인 기능
     */
    @ApiOperation(value = "카카오 로그인", notes = "카카오 accessToken 이용해서 사용자 정보 불러오기")
    @GetMapping("/kakao/callback")
    public ResponseEntity<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(kakaoAuthService.login(authRequest));
    }

    /**
     * NAVER 소셜 로그인 기능
     */
    @ApiOperation(value = "네이버 로그인", notes = "네이버 accessToken 이용해서 사용자 정보 불러오기")
    @PostMapping("/naver")
    public ResponseEntity<AuthResponse> naverAuthRequest(@RequestBody AuthRequest authRequest) {
        return ApiResponse.success(naverAuthService.login(authRequest));
    }

    /**
     * accessToken 갱신
     */
    @ApiOperation(value = "accessToken(JWT) 갱신", notes = "accessToken(JWT) 갱신")
    @GetMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(HttpServletRequest request) {
        String jwtToken = JwtHeaderUtil.getAccessToekn(request);
        AuthToken authToken = authToken

    }







    /*//로그인 처리 후에 사용자의 정보를 OAuth에서 얻어 반환
    @GetMapping("/login/{provider}")
    public void oauthLogin(@PathVariable String provider, HttpServletResponse response)throws IOException {
        log.info("OAuth2 Login");
        String redirect_uri = "http://localhost:8080/oauth2/authorization/" + provider;

        response.sendRedirect(redirect_uri);
    }

    //OAuth2 성공 - redirect uri로 token 반환
    @GetMapping("/token")
    public ResponseEntity<JsonResponse> token(@PathParam("token") String accessToken){
        log.info("OAuth2 Login Token Response");
        return ResponseEntity.ok(new JsonResponse(200,"login-getToken", accessToken));
    }*/
}
