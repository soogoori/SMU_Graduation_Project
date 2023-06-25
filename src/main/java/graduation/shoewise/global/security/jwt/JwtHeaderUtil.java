package graduation.shoewise.global.security.jwt;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class JwtHeaderUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // HTTP Request Header 에서 토큰 정보를 꺼내오기
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(AUTHORIZATION_HEADER);

        log.info("headerValue : " + headerValue);

        if (headerValue == null) {

            log.info("headerValue is null");
            return null;
        }

        if (headerValue.startsWith(BEARER_PREFIX)) {
            return headerValue.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
