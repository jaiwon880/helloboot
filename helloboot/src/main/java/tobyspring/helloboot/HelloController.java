package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

public class HelloController {
    public String hello(String name) { // String -> plain
        SimpleHelloService helloService = new SimpleHelloService();

        // null 일 때, 파라미터 전달이 잘못되었을 때
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
