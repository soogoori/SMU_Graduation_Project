package graduation.shoewise.global.security.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Slf4j
public class SecurityUtil {

    /**
     * [인증 유저의 PK]
     * 현재 로그인한 유저의 PK를 얻어옴
     * @return [String] PK
     */
    public static Long getCurrentMemberPk() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("authentication.getPrincipal() : " + authentication.getPrincipal().toString());
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long userPk = userPrincipal.getId();
        log.info("userPK :" + userPk.toString());

        return userPk;
    }
}
