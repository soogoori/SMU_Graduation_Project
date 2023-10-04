package graduation.shoewise.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 프론트엔드 클라이언트가 다른 출처의 API에 액세스 할 수 있도록 CORS를 활성화
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsConfiguration corsConfiguration;

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") //외부에서 들어오는 모든 url 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") //허용되는 Method
                .allowedHeaders("*")  //허용되는 헤더
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS); //허용 시간
    }
}