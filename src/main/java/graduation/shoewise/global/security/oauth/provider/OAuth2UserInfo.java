package graduation.shoewise.global.security.oauth.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getProviderId(); //소셜 식별 값
    public abstract String getEmail();
    public abstract String getName();
}
