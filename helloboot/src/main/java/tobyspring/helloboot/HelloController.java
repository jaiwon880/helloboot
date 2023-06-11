package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello") // Get Method로 들어오는 것 중 "/" URL 지정
    // @RequestMapping(value="/hello", method = RequestMethod.GET) 이전에 쓰던 방식
    public String hello(String name) { // String -> plain

        // null 일 때, 파라미터 전달이 잘못되었을 때
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
