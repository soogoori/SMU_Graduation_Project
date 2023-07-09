package graduation.shoewise.domain.main;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    @ApiOperation(value = "main home", notes = "메인화면")

    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
