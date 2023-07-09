package graduation.shoewise.domain.user.controller;

import graduation.shoewise.global.security.jwt.AppProperties;
import graduation.shoewise.global.security.jwt.AuthTokenProvider;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    /*private final AppProperties appProperties;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse login(HttpServletRequest request, HttpServletResponse response,
                             @RequestBody AuthReqModel )*/

}
