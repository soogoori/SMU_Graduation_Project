package graduation.shoewise.global.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller // 개파필
@RequiredArgsConstructor
public class BaseController {

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final String logoutURL = "https://localhost:8080/logout/kakao";

    @GetMapping("/")
    public String index() {

        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    /*@GetMapping("/login")
    public String getLoginPage(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "login";
    }*/

    @RequestMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }


    @GetMapping("/logout/kakao")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String accessToken = (String)session.getAttribute("access_token");
        log.info(accessToken);

        if (accessToken != null && !"".equals(accessToken)) {
            try {
                URL url = new URL(logoutURL);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Bearer " + accessToken);

                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.removeAttribute("access_token");
            session.removeAttribute("user");

            log.info("logout success");
        }

        return "redirect:/";
    }
    // http://nid.naver.com/nidlogin.logout 으로 로그아웃 진행
}
