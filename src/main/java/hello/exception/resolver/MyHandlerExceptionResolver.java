package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //IllegalArgumentException 발생하는 경우 response.sendError로 HTTP 상태를 400으로 지정하고 ModelAndView를 반환한다.
        if (ex instanceof IllegalArgumentException) {
            try {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //예외가 발생해도 정상적인 흐름으로 리턴.
                return new ModelAndView();
            } catch (IOException e) {
                log.error("resolver ex", e);
            }
        }
        //null을 리턴하면 계속 예외가 발생.
        return null;
    }
}
