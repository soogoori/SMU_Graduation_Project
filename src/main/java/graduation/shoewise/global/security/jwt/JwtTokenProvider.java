package graduation.shoewise.global.security.jwt;

import graduation.shoewise.domain.user.dto.UserTokenDto;
import graduation.shoewise.global.config.ExpireTime;
import graduation.shoewise.global.security.oauth.service.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


/**
 * JWT 생성 및 유효성 검증 → Secret Key를 이용해서 토큰 생성
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private final Key key;
    private final UserDetailServiceImpl userDetailServiceImpl;

    public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey, UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public UserTokenDto.TokenInfo generateToken(Authentication authentication) {
        return generateToken(authentication.getName(), authentication.getAuthorities());
    }

    //name, authorities 를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public UserTokenDto.TokenInfo generateToken(String name, Collection<? extends GrantedAuthority> inputAuthorities) {

        //권한 가져오기
        String authorities = inputAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");


        //Generate AccessToken
        String accessToken = Jwts.builder()
                .setSubject(name)
                .setHeader(headers)
                .claim(AUTHORITIES_KEY, authorities)
                .claim("type", TYPE_ACCESS)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ExpireTime.ACCESS_TOKEN_EXPIRE_TIME))  //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //Generate RefreshToken
        String refreshToken = Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ExpireTime.REFRESH_TOKEN_EXPIRE_TIME)) //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return UserTokenDto.TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpirationTime(ExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(ExpireTime.REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    // jwt access Token을 검사하고 얻은 정보로 Authentication 객체 생성 → JWT로 인증정보 조회
    public Authentication getAuthentication(String accessToken) {

        /**
         * claim 정보에는 토큰에 부가적으로 실어 보낼 정보를 담음 → 회원을 구분할 수 있는 값 세팅
         * 토큰이 들어오면 회원을 구분해서 리소스 제공
         */

        //토큰 복호화
        Claims claims = parseClaims(accessToken);
        log.info("claim 토큰 복호화 : " +claims.getSubject());

        if (claims.get(AUTHORITIES_KEY) == null) {
            //TODO:: Change Custom Exception
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        //클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        log.info("클레임 출력출력 ROLE : " + claims.get(AUTHORITIES_KEY).toString());
        log.info("클레임 출력출력 : " + claims.getSubject());

        //UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = userDetailServiceImpl.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    //토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            // ???
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /*public String resolveToken(HttpServletRequest request) {
        log.info("request.getHeader: " + request.getHeader("Authorization"));
        return request.getHeader("Authorization");
    }*/

    // 엑세스 토큰 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("authorization", "Bearer " + accessToken);
    }

}
