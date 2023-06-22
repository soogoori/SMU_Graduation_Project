package graduation.shoewise.global.security.jwt;

import graduation.shoewise.global.security.oauth.token.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT이 유효한 토큰인지 인증하기 위한 Filter
 * Security 설정시 UsernamePasswordAuthentication 앞에 세팅해서 로그인 폼으로 반환하기 전에 인증여부를 JSON으로 반환
 */
// 로그인시 JWT Token을 확인해 인가된 사용자 유무를 판별하고 내부 process를 수행
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthTokenProvider tokenProvider;

    // request 로 들어오는 JWT의 유효성 검증
    // JwtTokenProvider.validateToken()을 필터로서 FilterChain에 추가
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Request Header 에서 토큰을 꺼냄
        String tokenStr = JwtHeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        log.info("[Verifying token] - JwtRequestFilter 진입");
        log.info("RequestURI: " + request.getRequestURI().toString());
        log.info("JWT : " + tokenStr);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (token.validate()) {
            // 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
            Authentication authentication = tokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체 저장 → 해당 유저는 Authenticate(인증) 완료
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
