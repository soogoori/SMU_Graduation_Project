package graduation.shoewise.global.security.oauth.service;

import graduation.shoewise.domain.enums.ProviderType;
import graduation.shoewise.domain.enums.RoleType;
import graduation.shoewise.domain.user.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * User를 생성자로 전달 받아서 Spring Security에 User 정보 전달
 * 인증된 spring security 주체를 나타냄.
 */
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private Long id;
    private String email;
    private RoleType roleType;
    private ProviderType providerType;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }


    public static UserPrincipal create(User user){
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.ADMIN.getCode()))
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.name()));
        UserPrincipal userPrincipal = new UserPrincipal(user.getId(), user.getEmail(), authorities);

        log.info("유저네임 프린시플 : " + userPrincipal.getUsername());
        log.info("유저 겟네임 프린시플 : " + userPrincipal.getName());
        log.info("유저이메일 프린시플 : " + userPrincipal.getEmail());
        log.info("유저속성 프린시플 : " + userPrincipal.getAttributes());
        log.info("유저 권한 프린시플 : " + userPrincipal.getAuthorities());

        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes=attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
