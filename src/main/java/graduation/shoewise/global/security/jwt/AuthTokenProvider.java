package graduation.shoewise.global.security.jwt;

import graduation.shoewise.domain.enums.RoleType;
import graduation.shoewise.global.security.UserPrincipal;
import graduation.shoewise.global.security.exception.TokenValidFailedException;
import graduation.shoewise.global.security.oauth.UserDetailsServiceImpl;
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

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * JWT 생성 및 유효성 검증 → Secret Key를 이용해서 토큰 생성
 */
@Slf4j
@Component
public class AuthTokenProvider {



    private final String secretKey = "56Kjdfkw8wKFKnlkfjwfk3k592JN4Nvlskngf29Nlglsdkfw2dlwfkfos84lsKgbiYCTe20g92l6BBKClsog50ad2lgk8sJsPeiqpdkjospwfjskcpskjd27308lflll2l5knlvihdskdfnkcodfkjleriert934nKKDHENCBXMO25245gak90asl";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    private final String AUTHORITIES_KEY = "role";

    private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 10;		// 10min 토큰 만료 시간
    private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
    private static final String BEARER_TYPE = "Bearer";
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /*public AuthTokenProvider(@Value("${app.auth.tokenSecret}") String secret) {
        this.key= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }*/

    /**
     * AccessToken 생성 메소드
     */
    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

        log.info("create access token - tokenProvider " + authentication.getPrincipal().toString());
        log.info("create access token - tokenProvider " + authentication.getName());

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public AuthToken createAuthToken(Long id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(Long id, RoleType roleType, Date expiry) {
        return new AuthToken(id, roleType, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
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

            log.debug("claims subject := [{}]", claims.getSubject());
            log.info("getAuthentication() :" + authorities.toString());

            // 스프링 Security 내부 인증용으로 사용하는 principal 객체
            // User principal = new User(claims.getSubject(), "", authorities);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authToken.getTokenClaims().getSubject());

            return new UsernamePasswordAuthenticationToken(userDetails, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}
