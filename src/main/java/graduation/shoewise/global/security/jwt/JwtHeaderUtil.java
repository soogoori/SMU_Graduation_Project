package graduation.shoewise.global.security.jwt;

import javax.servlet.http.HttpServletRequest;

public class JwtHeaderUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // HTTP Request Header 에서 토큰 정보를 꺼내오기

    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(AUTHORIZATION_HEADER);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(BEARER_PREFIX)) {
            return headerValue.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
