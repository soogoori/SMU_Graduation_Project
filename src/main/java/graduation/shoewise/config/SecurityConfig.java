package graduation.shoewise.config;

import graduation.shoewise.security.cookie.CookieAuthorizationRequestRepository;
import graduation.shoewise.security.jwt.JwtAuthenticationEntryPoint;
import graduation.shoewise.security.jwt.JwtAuthenticationFilter;
import graduation.shoewise.security.oauth.handler.OAuth2AuthenticationFailureHandler;
import graduation.shoewise.security.oauth.handler.OAuth2AuthenticationSuccessHandler;
import graduation.shoewise.security.oauth.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor // 스프링 컨테이너에 빈 등록
//@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //요청에 대한 권한 설정
        http.authorizeHttpRequests()
                .antMatchers( "/css/**", "/images/**", "/js/**", // 권한 관리 대상 지정
                        "/login/**", "/token/**", "/oauth2/**").permitAll()
                .antMatchers("/user").hasRole("USER")
                .and().logout().logoutSuccessUrl("/")
                .and()
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());

        http.oauth2Login() // OAuth2기반의 로그인인 경우
                .defaultSuccessUrl("/home") // oauth2 인증 성공하면 이동되는 url
//                .failureUrl("/login")// 로그인 실패 시 /login으로 이동
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .userInfoEndpoint()// 로그인 성공 후 사용자정보를 가져온다
                .userService(oAuth2UserService);

        http.logout()
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID");

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
