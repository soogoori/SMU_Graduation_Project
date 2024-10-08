package graduation.shoewise.global.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    // request 로 들어오는 JWT의 유효성 검증
    // JwtTokenProvider.validateToken()을 필터로서 FilterChain에 추가
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //요청값의 header에서 토큰을 뽑아온다.
        String token = jwtTokenProvider.resolveToken(request); // Bearer로 시작하는 값에서 Bearer를 제거한 accessToken(appToken) 반환


        log.info("[Verifying token] - JwtRequestFilter 진입");
        log.info("RequestURI: " + request.getRequestURI().toString());
        log.info("JWT tokenStr : " + token);

        // 2. validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보 받아오기
            log.info("토큰이 유효합니당 유저정보 받아올게용");

            // 토큰이 유효할 경우 토큰에서 authentication 객체 갖고와서 SecurityContext에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체 저장 → 해당 유저는 Authenticate(인증) 완료
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
