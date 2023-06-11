package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {
    public static void main(String[] args) {
        // 스프링 컨테이너
        GenericApplicationContext applicationContext= new GenericApplicationContext();
        // Bean 등록
        applicationContext.registerBean(HelloController.class);
        applicationContext.refresh(); // Bean Object를 만듦.

        // 톰캣없이 서블릿 구현하기
        // 빈 서블릿 컨테이너 구현
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // 웹서버 생성
        // 람다식 변환
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("frontcontroller", new HttpServlet() {
                // service 메소드: 웹프로그래밍 -> 웹 요청을 받아 응답하는 것
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // 인증, 보안, 다국어, 공통 기능
                    // Mapping: 요청을 가지고 함. Method, Path, Header, Body
                    // /hello로 들어왔는데 POST 방식 등이 들어오면 처리할 수 없다
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        String name = req.getParameter("name");

                        HelloController helloController = applicationContext.getBean(HelloController.class);
                        String ret = helloController.hello(name);

                        // resp.setStatus(HttpStatus.OK.value()); // default 200 값
                        resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                        // Body
                        resp.getWriter().println(ret);
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
                //모든 요청을 서블릿이 컨트롤러를 하겠다. -> frontcontroller
            }).addMapping("/*");

        });
        // Tomcat 서블릿 컨테이너 동작
        webServer.start();
    }
}
