package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


//@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    // 기본 오류 페이지를 customize 한다.
    public void customize(ConfigurableWebServerFactory factory) {
        //ErrorPage(오류 코드, 이동 할 페이지경로) 생성
        //-> 해당 오류 코드가 발생하면 페이지 경로로 이동하라고 설정하는 에러페이지임.
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");
        //에러 페이지 등록. (factory.addErrorPages)
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);

    }
}
