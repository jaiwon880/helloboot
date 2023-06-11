package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration // 구성 정보
public class HellobootApplication {
    // Spring Container가 Bean Object를 만들기 위해 쓰이는 것이라고 알려줌
    // @Bean
    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }

    @Bean
    // 인터페이스 타입으로 return
    public HelloService helloService() {
        return new SimpleHelloService();
    }

    public static void main(String[] args) {
        // 스프링 컨테이너
        // 웹 환경에서 쓰도록 WebApplicationContext 타입을 사용
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
                // 웹서버 생성
                // 람다식 변환
                WebServer webServer = serverFactory.getWebServer(servletContext -> {
                    servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/*");

                });
                // Tomcat 서블릿 컨테이너 동작
                webServer.start();
            }
        };
        applicationContext.register(HellobootApplication.class); // Bean Object 등록
        applicationContext.refresh(); // Bean Object를 만듦.

    }
}
