package graduation.shoewise.global.security.oauth.service;

import graduation.shoewise.domain.enums.ProviderType;
import graduation.shoewise.domain.enums.RoleType;
import graduation.shoewise.global.security.UserPrincipal;
import graduation.shoewise.global.security.exception.OAuthProviderMissMatchException;
import graduation.shoewise.global.security.oauth.OAuthAttributes;
import graduation.shoewise.global.security.oauth.provider.OAuth2UserInfo;
import graduation.shoewise.global.security.oauth.provider.OAuth2UserInfoFactory;
import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2UserService 클래스는 사용자의 정보들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능 지원
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    // OAuth2 인증 후 받는 권한 정보(이름, 이메일 등) 처리 함수
    // 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행됨

    /**
     * access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다
     *
     * 해당 사용자가 이미 회원가입 되어있는 사용자인지 확인 → 만약 회원가입이 되어있지 않다면, 회원가입 처리
     * 만약 회원가입이 되어있다면 가입한 적 있다는 log를 찍고 로그인
     */

    /**
     * UserPrincipal을 return함.
     */

    private final UserRepository userRepository;

    /**
     * loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
     * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환
     * OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
     */

    /**
     * loadUser 메서드가 호출되었을 때 OAuth2UserRequest 객체에는 oauth 인증 결과인 access token을 포함
     * 해당 access token을 통해 oAuth2User 객체 정보를 얻어오는데,
     * 해당 객체에는 리소스 서버에서 받아온 사용자에 대한 정보가 포함되어있음
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2User oAuth2User = super.loadUser(userRequest);// access token을 이용해 서버로부터 사용자 정보를 받아온다.

        try {
            return this.process(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    // 획득한 유저정보를 Entity Model과 mapping 후 프로세스 진행
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        log.info("OAuth2 Login - process");
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase()); // 서비스 구분 - NAVER, KAKAO
        log.info("OAuth2 Login - process : " + providerType.toString()); // NAVER or KAKAO 출력

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, oAuth2User.getAttributes());
        log.info("userInfo : " + userInfo.getProviderId() + " " + userInfo.getEmail() + " " + userInfo.getName());

        OAuthAttributes attributes = OAuthAttributes.of(providerType, oAuth2User.getAttributes());
        log.info("attributes : " + attributes.getEmail() + " " + attributes.getName() + " " + attributes.getNameAttributeKey());

        User savedUser = userRepository.findByProviderId(userInfo.getProviderId());

        if (savedUser != null) { // 이미 가입한 경우
            log.info("OAuth2 - process : " + providerType.toString());
            if (providerType != savedUser.getProvider()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.getProvider() + " account to login."
                );
            }
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, oAuth2User.getAttributes());
    }

    /**
     ** 생성된 User 객체를 DB에 저장
     */
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        log.info("DB에 유저 객체 저장");
        User user = new User(
                userInfo.getName(),
                userInfo.getEmail(),
                providerType,
                userInfo.getProviderId(),
                RoleType.USER
        );

        return userRepository.save(user);
    }
}
