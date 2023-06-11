package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {
    public static void main(String[] args) {
        // 톰캣없이 서블릿 구현하기
        // 빈 서블릿 컨테이너 구현
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // 웹서버 생성
        // 람다식 변환
        WebServer webServer = serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("hello", new HttpServlet() {
                // service 메소드: 웹프로그래밍 -> 웹 요청을 받아 응답하는 것
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // 정상 응답
                    resp.setStatus(200);
                    resp.setHeader("Content-Type", "text/plain");
                    // Body
                    resp.getWriter().println("Hello Servlet");
                }
            }).addMapping("/hello");

        });
        // Tomcat 서블릿 컨테이너 동작
        webServer.start();
    }
}
