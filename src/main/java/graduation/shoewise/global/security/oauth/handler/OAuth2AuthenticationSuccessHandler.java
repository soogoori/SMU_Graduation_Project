package graduation.shoewise.global.security.oauth.handler;

import graduation.shoewise.global.security.cookie.CookieAuthorizationRequestRepository;
import graduation.shoewise.global.security.cookie.CookieUtil;
import graduation.shoewise.global.security.exception.BadRequestException;
import graduation.shoewise.global.security.jwt.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;

    private final AuthTokenProvider tokenProvider;
    private final CookieAuthorizationRequestRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 Login 성공!");

//        login 성공한 사용자 목록.
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User.toString());

        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("토큰 발행 시작 ↑ JWT 생성");
        log.info("targetUrl1 : " + targetUrl);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        log.info("clearAuthenticationAttributes");
        getRedirectStrategy().sendRedirect(request, response, targetUrl); // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
        log.info("sendRedirect");
    }

    // token을 생성하고 이를 포함한 프론트엔드의 URI를 생성한다.
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("redirect URIs are not matched");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        log.info("targetUrl2 : " + targetUrl);

        // JWT 생성
        String accessToken = tokenProvider.createToken(authentication);
        log.info("OAuth2 SuccessHandler - determineTargetUrl: " + accessToken);
        //log.info("Refresh Token : " + accessToken);

        //tokenProvider.createRefreshToken(authentication, response);

        // targetUrl에 accessToken 값을 쿼리 파라미터로 추가한 새로운 URL을 생성
        //생성된 URL은 로그인 성공 후 리다이렉트할 URL에 accessToken 값을 함께 전달
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .build().toUriString();
    }

    // 로그인을 하는 과정에서 한번만에 로그인에 성공할 수도 있지만,
    // 실패를 한 후 로그인에 성공하는 경우도 있다.
    // 이처럼 로그인에 실패하는 상황이 한번이라도 발생한다면, 에러가 세션에 저장되어 남아있게 된다.
    //로그인에 성공했다고 하지만 이렇게 세션에 에러가 남겨진 채로 넘어갈 수는 없다.
    //따라서 로그인 성공 핸들러에서 에러 세션을 지우는 작업을 해줘야 한다.
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort()) {
            return true;
        }
        return false;
    }
}
