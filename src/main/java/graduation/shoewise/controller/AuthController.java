package graduation.shoewise.controller;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import graduation.shoewise.domain.JsonResponse;
import graduation.shoewise.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    //로그인 처리 후에 사용자의 정보를 OAuth에서 얻어 반환
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
    }
}
