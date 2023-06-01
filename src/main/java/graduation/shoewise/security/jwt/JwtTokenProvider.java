package graduation.shoewise.security.jwt;

import graduation.shoewise.repository.UserRepository;
import graduation.shoewise.security.UserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class JwtTokenProvider {

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private final String SECRET_KEY;
    private final Key key;
    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 10;		// 10min 토큰 만료 시간
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
    private final String AUTHORITIES_KEY = "role";
    private static final String BEARER_TYPE = "Bearer";


    public JwtTokenProvider(@Value("${jwt.secretKey}")String secretKey) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * AccessToken 생성 메소드
     */
    public JwtToken createToken(Authentication authentication) {
        Date now = new Date();

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        String userId = user.getName();

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String refreshToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +REFRESH_TOKEN_EXPIRE_LENGTH))
                .compact();


        String accessToken= Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .setSubject(userId)
                .claim(AUTHORITIES_KEY, role)
                .setIssuedAt(now) // token 발행 시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH))
                .compact();

        return JwtToken.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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

/*
    private void saveRefreshToken(Authentication authentication, String refreshToken) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        userRepository.updateRefreshToken(id, refreshToken);
    }
*/


    // jwt access Token을 검사하고 얻은 정보로 Authentication 객체 생성 → JWT로 인증정보 조회
    public Authentication getAuthentication(String accessToken) {

        /**
         * claim 정보에는 토큰에 부가적으로 실어 보낼 정보를 담음 → 회원을 구분할 수 있는 값 세팅
         * 토큰이 들어오면 회원을 구분해서 리소스 제공
         */
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // token 유효성 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key)
                    .build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
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
