package graduation.shoewise.controller;

import graduation.shoewise.config.BaseException;
import graduation.shoewise.entity.user.dto.UserRequestDto;
import graduation.shoewise.entity.user.dto.UserResponseDto;
import graduation.shoewise.security.oauth.token.OAuthToken;
import graduation.shoewise.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static graduation.shoewise.security.jwt.JwtAuthenticationFilter.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/oauth/token")
    public OAuthToken login(@RequestParam("code") String code){
        // 넘어온 인가 코드를 통해 access_token 발급
        OAuthToken oauthToken = userService.getAccessToken(code);

        // 발급 받은 accessToken 으로 카카오 회원 정보 DB 저장
        String user = userService.saveUser(oauthToken);

        return oauthToken;
    }

    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("access_token");

        if (accessToken != null && !"".equals(accessToken)) {
            try {
                URL url = new URL("http://nid.naver.com/nidlogin.logout");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty(AUTHORIZATION, BEARER_PREFIX + accessToken);

                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.removeAttribute("access_token");
            session.removeAttribute("user");

            log.info("logout success");
        }

        return "redirect:/";
    }

    // 프로필 수정
    @PatchMapping("/user/mypage")
    public UserResponseDto updateMypage(@RequestParam("id") Long id,
                                        @RequestBody UserRequestDto requestDto) throws BaseException {

        return userService.updateUser(id, requestDto);

    }

    //프로필 조회
    @GetMapping("/user/mypage")
    public UserResponseDto getUserInfo(
           @RequestParam("id") Long id) throws Exception{

        return userService.getUser(id);
    }

}
