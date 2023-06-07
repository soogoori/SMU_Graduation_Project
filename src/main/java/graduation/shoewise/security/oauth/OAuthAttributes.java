package graduation.shoewise.security.oauth;

import graduation.shoewise.entity.user.User;
import graduation.shoewise.entity.enums.ProviderType;
import graduation.shoewise.entity.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String providerId;
    private String email;
    private ProviderType provider;

    public static OAuthAttributes of(ProviderType provider, Map<String, Object> attributes) {
        if("NAVER".equals(provider.toString())) {
            return ofNaver("id", attributes);
        }
        return ofKakao("id", attributes);

    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .providerId((String)attributes.get("id"))
                .provider(ProviderType.NAVER)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> account = (Map<String, Object>) response.get("profile");

        return OAuthAttributes.builder()
                .name((String) account.get("nickname"))
                .email((String) response.get("email"))
                .provider(ProviderType.KAKAO)
                .providerId(attributes.get("id").toString())
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .nickname(name)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(RoleType.USER)
                .build();
    }
}
