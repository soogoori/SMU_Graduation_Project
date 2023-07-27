package graduation.shoewise.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "Swagger API 명세서 목록";
    private static final String API_DESCRIPTION = "Swagger API 명세서 Test 중...";
    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .select()
                .apis(RequestHandlerSelectors.basePackage("graduation.shoewise.domain")) // swagger API 문서로 만들기 원하는 BasePackage 경로.
                .paths(PathSelectors.any()) // URL 경로를 지정하여 해당 URL에 해당하는 요청만 Swagger API 문서로 만들기
                .build();
    }

    // HTTP 요청시 어떤 문서 타입을 지정할 수 있는지
    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    // 응답 객체 반환시 어떤 문서 타입으로 반환 하는지
    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .build();
    }

}
