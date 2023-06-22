package graduation.shoewise.global.security.service;

import graduation.shoewise.domain.user.repository.UserRepository;
import graduation.shoewise.global.security.UserPrincipal;
import graduation.shoewise.global.security.cookie.CookieUtil;
import graduation.shoewise.global.security.jwt.JwtToken;
import graduation.shoewise.global.security.jwt.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static graduation.shoewise.global.security.cookie.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthTokenProvider tokenProvider;

    public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("no Refresh Token Cookie"));

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new RuntimeException("Not Validated Refresh Token");
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        Long id = Long.valueOf(user.getName());

        // 3. Match Refresh Token
        String savedToken = userRepository.getRefreshTokenById(id);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new RuntimeException("Not Matched Refresh Token");
        }

        // 4. JWT 갱신
        JwtToken accessToken = tokenProvider.createToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        return accessToken.getRefreshToken();
    }
}

