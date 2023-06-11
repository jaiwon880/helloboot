package tobyspring.helloboot;

import java.util.Objects;

public class HelloController {
    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) { // String -> plain

        // null 일 때, 파라미터 전달이 잘못되었을 때
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
