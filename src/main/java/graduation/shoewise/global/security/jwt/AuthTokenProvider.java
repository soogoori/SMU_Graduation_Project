package graduation.shoewise.global.security.jwt;

import graduation.shoewise.domain.enums.RoleType;
import graduation.shoewise.global.security.UserPrincipal;
import graduation.shoewise.global.security.exception.TokenValidFailedException;
import graduation.shoewise.global.security.oauth.token.AuthToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * JWT 생성 및 유효성 검증 → Secret Key를 이용해서 토큰 생성
 */
@Slf4j
@Component
public class AuthTokenProvider {

    private final Key key;
    private final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(@Value("${jwt.secretKey}")String secretKey) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


    private final String SECRET_KEY;
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 10;		// 10min 토큰 만료 시간
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
    private static final String BEARER_TYPE = "Bearer";



    /**
     * AccessToken 생성 메소드
     */
    public AuthToken createToken(String id, RoleType roleType, Date expiry) {
        return new AuthToken(id, roleType, expiry, key);
    }

    public AuthToken createUserToken(String id, Date expiry) {
        return createToken(id, RoleType.USER, expiry);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }


    // jwt refresh 토큰 생성
    public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

        String refreshToken = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();

//        saveRefreshToken(authentication, refreshToken);
//
//        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
//                .httpOnly(true)
//                .secure(true)
//                .sameSite("Lax")
//                .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH/1000)
//                .path("/")
//                .build();
//
//        response.addHeader("Set-Cookie", cookie.toString());
    }


    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        userRepository.updateRefreshToken(id, refreshToken);
    }



    // jwt access Token을 검사하고 얻은 정보로 Authentication 객체 생성 → JWT로 인증정보 조회
    public Authentication getAuthentication(AuthToken authToken) {

        /**
         * claim 정보에는 토큰에 부가적으로 실어 보낼 정보를 담음 → 회원을 구분할 수 있는 값 세팅
         * 토큰이 들어오면 회원을 구분해서 리소스 제공
         */
        if (authToken.validate()) {
            Claims claims = authToken.getTokenClaims();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }


    // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
